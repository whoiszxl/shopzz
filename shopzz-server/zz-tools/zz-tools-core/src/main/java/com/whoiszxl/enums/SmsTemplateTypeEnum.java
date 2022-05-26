package com.whoiszxl.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 短信模板类型枚举
 * @author whoiszxl
 * @date 2021/3/17
 */
@Getter
@AllArgsConstructor
public enum SmsTemplateTypeEnum {

    VERIFICATION(1, "验证码"),
    PROMOTION(2, "营销"),
    ;
    private Integer code;
    private String desc;
}