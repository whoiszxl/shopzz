package com.whoiszxl.taowu.im.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 好友请求审核状态枚举
 * 业务状态: 0-未审核 1-审核通过 2-审核拒绝
 * @author whoiszxl
 */
@Getter
@AllArgsConstructor
public enum FriendRequestStatusEnum {

    /**
     * 未审核
     */
    NOT_AUDIT(0, "未审核"),
    /**
     * 审核通过
     */
    AUDIT_SUCCESS(1, "审核通过"),

    /**
     * 审核拒绝
     */
    AUDIT_FAIL(2, "审核拒绝"),
    ;
    private final Integer code;
    private final String desc;
}