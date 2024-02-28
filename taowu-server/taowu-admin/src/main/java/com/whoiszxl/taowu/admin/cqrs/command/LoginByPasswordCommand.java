package com.whoiszxl.taowu.admin.cqrs.command;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author whoiszxl
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "密码登录命令")
public class LoginByPasswordCommand {

    @Schema(description = "用户名")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @Schema(description = "加密后的密码")
    @NotBlank(message = "密码不能为空")
    private String password;

    @Schema(description = "验证码")
    @NotBlank(message = "验证码不能为空")
    private String captcha;

    @Schema(description = "验证码标识")
    @NotBlank(message = "验证码标识不能为空")
    private String uuid;
}
