package com.whoiszxl.controller;


import cn.dev33.satoken.annotation.SaCheckLogin;
import com.whoiszxl.entity.Admin;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 登录日志 前端控制器
 * </p>
 *
 * @author whoiszxl
 * @since 2021-12-06
 */
@RestController
@RequestMapping("/admin-login-log")
//@Api(tags = "admin-login-log")
public class AdminLoginLogController {

    @SaCheckLogin
    @GetMapping("/list")
    @ApiOperation(value = "管理员列表查询", notes = "管理员列表查询", response = Admin.class)
    public String hello() {
        return "ok";
    }

}

