package com.whoiszxl.zero.controller;

import com.whoiszxl.zero.bean.Result;
import com.whoiszxl.zero.entity.params.LoginParam;
import com.whoiszxl.zero.entity.vo.LoginMemberVO;
import com.whoiszxl.zero.service.MemberLoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "会员登录控制器")
@RestController
public class LoginController {

    @Autowired
    private MemberLoginService memberLoginService;

    @PostMapping("/login")
    @ApiOperation(value = "后台管理员登录")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "loginParam", value = " 登录参数")
            }
    )
    public Result<LoginMemberVO> login(@RequestBody LoginParam loginParam) {
        return Result.buildSuccess(memberLoginService.login(loginParam));
    }
}
