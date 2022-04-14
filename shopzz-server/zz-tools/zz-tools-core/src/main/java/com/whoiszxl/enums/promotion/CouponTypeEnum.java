package com.whoiszxl.enums.promotion;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
* 优惠券类型枚举
*
* @author whoiszxl
* @date 2021/7/29
*/
@Getter
@AllArgsConstructor
public enum CouponTypeEnum {

    FULL_DISCOUNT(1, "满减券"),
    FULL_RATE(2, "满减折扣券"),
    UNLIMITED(3, "无门槛立减券")
    ;
    private Integer code;
    private String desc;

    public static CouponTypeEnum getType(Integer type) {
        for (CouponTypeEnum value : CouponTypeEnum.values()) {
            if(value.code.equals(type)) {
                return value;
            }
        }
        return null;
    }
}