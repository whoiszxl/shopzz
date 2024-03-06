package com.whoiszxl.taowu.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
* 预热状态枚举
*
* @author whoiszxl
* @date 2021/7/29
*/
@Getter
@AllArgsConstructor
public enum WarmUpStatusEnum {

    NO(0, "未预热"),
    YES(1, "已预热")
    ;
    private Integer code;
    private String desc;

    public static WarmUpStatusEnum getType(Integer type) {
        for (WarmUpStatusEnum value : WarmUpStatusEnum.values()) {
            if(value.code.equals(type)) {
                return value;
            }
        }
        return null;
    }
}