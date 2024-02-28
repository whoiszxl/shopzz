package com.whoiszxl.taowu.admin.controller.login;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import com.whoiszxl.taowu.common.constants.RedisPrefixConstants;
import com.whoiszxl.taowu.common.entity.ResponseResult;
import com.whoiszxl.taowu.common.utils.RedisUtils;
import com.whoiszxl.taowu.common.token.TokenHelper;
import com.whoiszxl.taowu.common.token.entity.LoginMember;
import com.whoiszxl.taowu.common.utils.SecureUtils;
import com.whoiszxl.taowu.admin.cqrs.command.LoginByPasswordCommand;
import com.whoiszxl.taowu.admin.cqrs.response.AdminDetailResponse;
import com.whoiszxl.taowu.admin.service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * @author whoiszxl
 */
@Tag(name = "member会员 接口")
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final LoginService loginService;

    private final RedisUtils redisUtils;

    private final TokenHelper tokenHelper;

    @SaIgnore
    @Operation(summary = "管理员后台登录", description = "根据管理员用户名和密码进行登录")
    @PostMapping("/login")
    public ResponseResult<String> login(@Validated @RequestBody LoginByPasswordCommand command) {
        //1. 验证码校验
        String captchaKey = RedisPrefixConstants.format(RedisPrefixConstants.Admin.ADMIN_CAPTCHA_IMAGE_KEY, command.getUuid());
        String captchaValue = redisUtils.get(captchaKey);
        Assert.notBlank(captchaValue, "验证码不存在");
        Assert.equals(captchaValue.toLowerCase(), command.getCaptcha().toLowerCase(), "验证码错误");

        //2. 登录
        String password = SecureUtils.decryptByRsaPrivateKey(command.getPassword());
        Assert.notBlank(password, "密码错误");

        String token = loginService.login(command.getUsername(), password);
        return ResponseResult.buildSuccess(token);
    }

    @Operation(summary = "获取管理员详情信息", description = "获取管理员详情信息")
    @GetMapping("/detail")
    public ResponseResult<AdminDetailResponse> getAdminDetail() {
        LoginMember loginMember = tokenHelper.getLoginMember();
        AdminDetailResponse adminDetailResponse = BeanUtil.copyProperties(loginMember, AdminDetailResponse.class);
        return ResponseResult.buildSuccess(adminDetailResponse);
    }

    @Operation(summary = "管理员注销", description = "管理员注销")
    @PostMapping("/logout")
    public ResponseResult<String> logout() {
        StpUtil.logout();
        return ResponseResult.buildSuccess();
    }

}
