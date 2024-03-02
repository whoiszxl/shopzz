package com.whoiszxl.zhipin.member.cqrs.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author whoiszxl
 */
@Data
@Schema(description = "短信登录命令")
public class SmsLoginCommand {

    @NotBlank
    @Size(min = 11, max = 11, message = "手机号长度错误")
    @Schema(description = "手机号")
    private String phone;

    @NotBlank
    @Size(min = 4, max = 4, message = "验证码长度错误")
    @Schema(description = "验证码")
    private String smsCode;

    @NotBlank
    @Schema(description = "UUID")
    private String uuid;
}
