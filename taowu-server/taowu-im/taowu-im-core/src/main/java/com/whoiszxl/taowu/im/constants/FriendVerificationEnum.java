package com.whoiszxl.taowu.im.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 添加好友验证枚举
 * @author whoiszxl
 */
@Getter
@AllArgsConstructor
public enum FriendVerificationEnum {

    /**
     * 需要验证
     */
    NEED_VERIFICATION(1, "需要验证"),

    /**
     * 无需验证
     *
     */
    NOT_NEED_VERIFICATION(2, "无需验证"),
    ;
    private final Integer code;
    private final String desc;
}