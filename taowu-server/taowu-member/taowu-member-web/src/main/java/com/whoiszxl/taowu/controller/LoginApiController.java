package com.whoiszxl.taowu.controller;


import cn.dev33.satoken.annotation.SaIgnore;
import com.whoiszxl.taowu.common.entity.ResponseResult;
import com.whoiszxl.taowu.service.ILoginService;
import com.whoiszxl.taowu.cqrs.command.SendSmsCaptchaCommand;
import com.whoiszxl.taowu.cqrs.command.SmsLoginCommand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 登录 前端控制器
 * </p>
 *
 * @author whoiszxl
 * @since 2023-08-04
 */
@Tag(name = "C端: 登录 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/login")
public class LoginApiController {

    private final ILoginService loginService;

    @SaIgnore
    @PostMapping("/sms-captcha")
    @Operation(summary = "获取短信验证码", description = "获取一个短信验证码")
    @ApiResponse(responseCode = "200", description = "短信验证码校验时的UUID")
    public ResponseResult<String> sendSmsCaptcha(@RequestBody SendSmsCaptchaCommand command) {
        return ResponseResult.buildSuccess(loginService.sendSmsCaptcha(command.getPhone()));
    }

    @SaIgnore
    @PostMapping("/sms")
    @Operation(summary = "短信验证码登录", description = "短信验证码登录")
    public ResponseResult<String> smsLogin(@RequestBody SmsLoginCommand command) {
        return ResponseResult.buildSuccess(loginService.smsLogin(command));
    }

}

