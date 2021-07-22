package com.whoiszxl.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.entity.SysUser;
import com.whoiszxl.entity.query.LoginQuery;
import com.whoiszxl.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "登录相关接口")
public class AdminLoginController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/admin/login")
    @ApiOperation(value = "账号密码登录", notes = "账号密码登录", response = ResponseResult.class)
    public ResponseResult<String> adminLogin(@RequestBody LoginQuery loginQuery) {
        //1. 从数据库查询用户名和密码是否匹配
        SysUser adminUser = sysUserService.getOne(new QueryWrapper<SysUser>().eq("username", loginQuery.getUsername()));
        if (adminUser == null || !passwordEncoder.matches(loginQuery.getPassword(), adminUser.getPassword())) {
            return ResponseResult.buildError("登录验证失败");
        }

        //2. 登录并获取token
        StpUtil.login(loginQuery.getUsername());
        return ResponseResult.buildSuccess("登录成功", StpUtil.getTokenValue());
    }

    @SaCheckLogin
    @DeleteMapping("/admin/logout")
    @ApiOperation(value = "管理员登出", notes = "管理员登出", response = ResponseResult.class)
    public ResponseResult<String> adminLogout() {
        StpUtil.logout();
        return ResponseResult.buildSuccess("登出成功");
    }
}