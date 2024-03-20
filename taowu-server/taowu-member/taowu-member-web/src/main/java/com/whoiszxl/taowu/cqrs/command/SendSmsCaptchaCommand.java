package com.whoiszxl.taowu.cqrs.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author whoiszxl
 */
@Data
@Schema(description = "短信发送命令")
public class SendSmsCaptchaCommand {

    @NotBlank
    @Schema(description = "手机号")
    private String phone;
}
