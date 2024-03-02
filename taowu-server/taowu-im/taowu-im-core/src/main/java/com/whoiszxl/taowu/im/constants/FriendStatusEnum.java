package com.whoiszxl.taowu.im.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 好友关系状态枚举
 * 业务状态: 1-正常 2-删除 3-黑名单
 * @author whoiszxl
 */
@Getter
@AllArgsConstructor
public enum FriendStatusEnum {

    /**
     * 正常
     */
    VALID(1, "正常"),

    /**
     * 删除
     *
     */
    DELETE(2, "删除"),

    /**
     * 黑名单
     *
     */
    BLACKLIST(3, "黑名单"),
    ;
    private final Integer code;
    private final String desc;
}