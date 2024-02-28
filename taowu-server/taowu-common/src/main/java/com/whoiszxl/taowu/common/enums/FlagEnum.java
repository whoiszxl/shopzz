package com.whoiszxl.taowu.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 标记枚举
 * 业务状态: 0-FALSE 1-TRUE
 * @author whoiszxl
 */
@Getter
@AllArgsConstructor
public enum FlagEnum {

    /**
     * 假
     */
    FALSE(0, "假"),

    /**
     * 真
     */
    TRUE(1, "真"),
    ;

    private final Integer code;
    private final String desc;
}