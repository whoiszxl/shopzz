package com.whoiszxl.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.entity.Member;
import com.whoiszxl.entity.query.LoginQuery;
import com.whoiszxl.service.MemberService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "登录相关接口")
public class LoginController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseResult<String> login(@RequestBody LoginQuery loginQuery) {
        //1. 从数据库查询用户名和密码是否匹配
        Member member = memberService.getOne(new QueryWrapper<Member>().eq("username", loginQuery.getUsername()));
        if(member == null || !passwordEncoder.matches(loginQuery.getPassword(), member.getPassword())) {
            return ResponseResult.buildError("登录验证失败");
        }

        //2. 登录并获取token
        StpUtil.login(loginQuery.getUsername());
        return ResponseResult.buildSuccess("登录成功", StpUtil.getTokenValue());
    }
}
