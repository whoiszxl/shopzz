package com.whoiszxl.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.concurrent.TimeUnit;

/**
 * 用户redis前缀枚举
 */
@Getter
@AllArgsConstructor
public enum UserRedisPrefixEnum {
    USER_REGISTER_PHONE_CODE("user:register:", 5, TimeUnit.MINUTES)
    ;
    private String key;
    private long time;
    private TimeUnit unit;

}