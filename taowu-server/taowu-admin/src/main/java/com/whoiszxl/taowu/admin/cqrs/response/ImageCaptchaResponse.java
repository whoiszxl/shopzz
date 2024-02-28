package com.whoiszxl.taowu.admin.cqrs.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author whoiszxl
 */
@Data
@Schema(description = "图片验证码返回实体")
@AllArgsConstructor
public class ImageCaptchaResponse {

    @Schema(description = "验证码唯一标识")
    private String uuid;

    @Schema(description = "Base64格式的验证码")
    private String captcha;

}
