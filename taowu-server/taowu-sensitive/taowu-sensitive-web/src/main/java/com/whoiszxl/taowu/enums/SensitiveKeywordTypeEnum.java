package com.whoiszxl.taowu.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 计数器新增减少状态枚举
 * @author whoiszxl
 * @date 2021/3/17
 */
@Getter
@AllArgsConstructor
public enum SensitiveKeywordTypeEnum {

    /**
     * 允许
     */
    ALLOW(1),

    /**
     * 禁止
     */
    DENY(2),
    ;

    private Integer code;
}