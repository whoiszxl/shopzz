package com.whoiszxl.taowu.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
* 秒杀商品状态枚举
*
* @author whoiszxl
* @date 2021/7/29
*/
@Getter
@AllArgsConstructor
public enum SeckillItemStatusEnum {

    CLOSE(0, "关闭"),
    OPEN(1, "开启")
    ;
    private Integer code;
    private String desc;

    public static SeckillItemStatusEnum getType(Integer type) {
        for (SeckillItemStatusEnum value : SeckillItemStatusEnum.values()) {
            if(value.code.equals(type)) {
                return value;
            }
        }
        return null;
    }
}