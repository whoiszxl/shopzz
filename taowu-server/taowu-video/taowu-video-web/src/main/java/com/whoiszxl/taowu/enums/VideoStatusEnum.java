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
public enum VideoStatusEnum {

    /**
     * 未发布
     */
    UNPUBLISHED(0, "未发布"),

    /**
     * 已发布
     */
    PUBLISHED(1, "已发布"),
    ;

    private Integer code;
    private String desc;
}