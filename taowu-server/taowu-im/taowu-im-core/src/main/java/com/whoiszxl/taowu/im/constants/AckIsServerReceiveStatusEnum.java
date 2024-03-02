package com.whoiszxl.taowu.im.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * receiveAck是否是服务端返回的状态枚举
 * 业务状态: 1-是 0-否
 * @author whoiszxl
 */
@Getter
@AllArgsConstructor
public enum AckIsServerReceiveStatusEnum {

    /**
     * 是
     */
    TRUE((byte)1),

    /**
     * 否
     */
    FALSE((byte)0),
    ;

    private final byte code;
}