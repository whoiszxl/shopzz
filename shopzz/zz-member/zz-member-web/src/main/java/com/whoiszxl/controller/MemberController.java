package com.whoiszxl.controller;


import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.dozer.DozerUtils;
import com.whoiszxl.entity.Member;
import com.whoiszxl.entity.MemberInfo;
import com.whoiszxl.entity.vo.*;
import com.whoiszxl.service.MemberInfoService;
import com.whoiszxl.service.MemberService;
import com.whoiszxl.utils.CheckPasswordUtils;
import com.whoiszxl.utils.RegexUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-29
 */
@RestController
@RequestMapping("/member")
@Api(tags = "用户相关接口")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberInfoService memberInfoService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private DozerUtils dozerUtils;

    @SaCheckLogin
    @GetMapping
    @ApiOperation(value = "获取当前登录用户的详细信息", notes = "获取当前登录用户的详细信息", response = MemberDetailVO.class)
    public ResponseResult<MemberDetailVO> memberInfo() {
        MemberDetailVO memberDetailVO = memberService.memberInfo();
        return ResponseResult.buildSuccess(memberDetailVO);
    }

    @SaCheckLogin
    @PutMapping
    @Transactional
    @ApiOperation(value = "更新当前登录用户的详细信息", notes = "更新当前登录用户的详细信息", response = Boolean.class)
    public ResponseResult<Boolean> updateMemberInfo(@RequestBody MemberDetailVO memberDetailVO) {
        long memberId = StpUtil.getLoginIdAsLong();

        //1. 更新用户信息
        MemberVO memberVO = memberDetailVO.getMember();
        Member member = dozerUtils.map(memberVO, Member.class);
        member.setId(memberId);
        memberService.updateById(member);

        //2. 更新用户详细信息
        MemberInfoVO memberInfoVO = memberDetailVO.getMemberInfo();
        MemberInfo memberInfo = dozerUtils.map(memberInfoVO, MemberInfo.class);
        memberInfo.setMemberId(memberId);
        memberInfoService.updateByMemberId(memberInfo);

        return ResponseResult.buildSuccess();
    }

    @Transactional
    @PostMapping("/password/register")
    @ApiOperation(value = "账号密码注册", notes = "账号密码注册", response = ResponseResult.class)
    public ResponseResult<Boolean> passwordRegister(@RequestBody PasswordRegisterQuery registerQuery) {
        //1. 校验参数
        if(RegexUtils.checkMobile(registerQuery.getUsername())) {
            return ResponseResult.buildError("手机号不正确");
        }

        //2. 校验密码是否一致
        if(!StringUtils.equals(registerQuery.getPassword(), registerQuery.getRePassword())) {
            return ResponseResult.buildError("两次输入密码不一致");
        }

        //3. 校验密码
        if(CheckPasswordUtils.getPasswordLevel(registerQuery.getPassword()).equals(CheckPasswordUtils.LEVEL.EASY)) {
            return ResponseResult.buildError("密码太简单了");
        }

        //4. 判断是否在数据库中已存在
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone", registerQuery.getUsername());
        Member member = memberService.getOne(queryWrapper);
        if(member != null) {
            return ResponseResult.buildError("用户已存在");
        }

        //5. 新增用户
        boolean registerFlag = memberService.passwordRegister(registerQuery.getUsername(), registerQuery.getPassword());
        return ResponseResult.buildByFlag(registerFlag);
    }


    @Transactional
    @PostMapping("/login")
    @ApiOperation(value = "账号密码登录", notes = "账号密码登录", response = ResponseResult.class)
    public ResponseResult<String> login(@RequestBody LoginQuery loginQuery) {
        //1. 从数据库查询用户名和密码是否匹配
        Member member = memberService.getOne(new QueryWrapper<Member>().eq("username", loginQuery.getUsername()));
        if(member == null || !passwordEncoder.matches(loginQuery.getPassword(), member.getPassword())) {
            return ResponseResult.buildError("账号或密码错误");
        }

        //2. 登录并获取token
        StpUtil.login(member.getId());

        //3. 更新最后登录时间
        MemberInfo memberInfo = new MemberInfo();
        memberInfo.setMemberId(member.getId());
        memberInfo.setLastLogin(new Date());
        memberInfoService.updateByMemberId(memberInfo);

        return ResponseResult.buildSuccess("登录成功", StpUtil.getTokenValue());
    }


    @SaCheckLogin
    @GetMapping("/logout")
    @ApiOperation(value = "登出", notes = "登出", response = ResponseResult.class)
    public ResponseResult<String> logout() {
        StpUtil.logout();
        return ResponseResult.buildSuccess();
    }
}

