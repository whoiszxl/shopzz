package com.whoiszxl.taowu.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
* 秒杀订单状态枚举
*
* @author whoiszxl
* @date 2021/7/29
*/
@Getter
@AllArgsConstructor
public enum SeckillOrderStatusEnum {

    NOT_PAY(0, "未支付"),
    PAID(1, "已支付"),
    CANCEL(2, "已取消"),
    ;
    private Integer code;
    private String desc;

    public static SeckillOrderStatusEnum getType(Integer type) {
        for (SeckillOrderStatusEnum value : SeckillOrderStatusEnum.values()) {
            if(value.code.equals(type)) {
                return value;
            }
        }
        return null;
    }
}