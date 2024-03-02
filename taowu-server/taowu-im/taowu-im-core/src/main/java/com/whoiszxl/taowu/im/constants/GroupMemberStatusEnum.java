package com.whoiszxl.taowu.im.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 群组会员状态枚举
 * 成员状态 1-正常 2-离开
 * @author whoiszxl
 */
@Getter
@AllArgsConstructor
public enum GroupMemberStatusEnum {

    /**
     * 正常
     */
    NORMAL(1, "正常"),

    /**
     * 离开
     */
    LEAVE(2, "离开"),
    ;
    private final Integer code;
    private final String desc;
}