package com.whoiszxl.controller;


import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.entity.Member;
import com.whoiszxl.entity.MemberInfo;
import com.whoiszxl.entity.vo.LoginQuery;
import com.whoiszxl.entity.vo.MemberDetailVO;
import com.whoiszxl.entity.vo.MemberInfoVO;
import com.whoiszxl.entity.vo.MemberVO;
import com.whoiszxl.service.MemberInfoService;
import com.whoiszxl.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

    @GetMapping
    @ApiOperation(value = "获取当前登录用户的详细信息", notes = "获取当前登录用户的详细信息", response = MemberDetailVO.class)
    public ResponseResult<MemberDetailVO> memberInfo() {
        MemberDetailVO memberDetailVO = memberService.memberInfo();
        return ResponseResult.buildSuccess(memberDetailVO);
    }

    @PutMapping
    @Transactional
    @ApiOperation(value = "更新当前登录用户的详细信息", notes = "更新当前登录用户的详细信息", response = Boolean.class)
    public ResponseResult<Boolean> updateMemberInfo(@RequestBody MemberDetailVO memberDetailVO) {
        long memberId = StpUtil.getLoginIdAsLong();

        //1. 更新用户信息
        MemberVO memberVO = memberDetailVO.getMember();
        Member member = memberVO.clone(Member.class);
        member.setId(memberId);
        memberService.updateById(member);

        //2. 更新用户详细信息
        MemberInfoVO memberInfoVO = memberDetailVO.getMemberInfo();
        MemberInfo memberInfo = memberInfoVO.clone(MemberInfo.class);
        memberInfo.setMemberId(memberId);
        memberInfoService.updateByMemberId(memberInfo);

        return ResponseResult.buildSuccess();
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

