package com.whoiszxl.taowu.im.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ack状态枚举
 * 业务状态: 1-成功 0-失败
 * @author whoiszxl
 */
@Getter
@AllArgsConstructor
public enum AckStatusEnum {

    /**
     * 成功
     */
    SUCCESS((byte)1, "成功"),

    /**
     * 失败
     */
    FAIL((byte)0, "失败"),
    ;

    private final byte code;
    private final String desc;
}