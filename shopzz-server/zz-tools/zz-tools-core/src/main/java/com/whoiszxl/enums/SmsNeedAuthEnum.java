package com.whoiszxl.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 短信是否需要鉴权状态类型
 * @author whoiszxl
 * @date 2021/3/17
 */
@Getter
@AllArgsConstructor
public enum SmsNeedAuthEnum {

    NEED(1, "需要"),
    NOT_NEED(0, "不需要"),
    ;
    private Integer code;
    private String desc;
}