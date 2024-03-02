package com.whoiszxl.taowu.im.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * RocketMQ主题枚举
 * @author whoiszxl
 */
@Getter
@AllArgsConstructor
public enum RocketMqTopicEnum {

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