package com.whoiszxl.taowu.im.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 连接状态枚举
 * 业务状态: 1-在线 2-离线
 * @author whoiszxl
 */
@Getter
@AllArgsConstructor
public enum ConnectStatusEnum {

    /**
     * 在线
     */
    ONLINE(1, "在线"),

    /**
     * 离线
     *
     */
    OFFLINE(2, "离线"),
    ;

    private final Integer code;
    private final String desc;
}