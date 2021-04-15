package com.whoiszxl.zero.controller;

import com.whoiszxl.zero.bean.Result;
import com.whoiszxl.zero.entity.params.LoginParam;
import com.whoiszxl.zero.entity.vo.LoginVO;
import com.whoiszxl.zero.service.SysLoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "系统管理员登录接口")
@RestController
public class SysLoginController {


    @Autowired
    private SysLoginService sysLoginService;

    @PostMapping("/login")
    @ApiOperation(value = "后台管理员登录")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "username", value = "用户名"),
                    @ApiImplicitParam(name = "password", value = "用户密码"),
            }
    )
    public Result<LoginVO> login(@RequestBody LoginParam loginParam) {
        return Result.buildSuccess(sysLoginService.login(loginParam));
    }

}
