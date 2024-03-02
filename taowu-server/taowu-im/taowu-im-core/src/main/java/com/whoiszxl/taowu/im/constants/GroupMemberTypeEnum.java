package com.whoiszxl.taowu.im.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 群组会员类型枚举
 * 成员类型: 1-普通成员 2-群主
 * @author whoiszxl
 */
@Getter
@AllArgsConstructor
public enum GroupMemberTypeEnum {

    /**
     * 普通成员
     */
    NORMAL(1, "普通成员"),

    /**
     * 群主
     *
     */
    OWNER(2, "群主"),
    ;
    private final Integer code;
    private final String desc;
}