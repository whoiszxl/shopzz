package com.whoiszxl.taowu.im.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Im Redis键枚举
 * @author whoiszxl
 */
@Getter
@AllArgsConstructor
public enum ImRedisKeysEnum {

    /**
     * 账号session key
     */
    MEMBER_SESSION_KEY("im:member_session:%s", 0L);

    private String prefix;

    private Long time;

}
