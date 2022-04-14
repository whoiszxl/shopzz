package com.whoiszxl.enums.promotion;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
* 优惠券状态枚举
*
* @author whoiszxl
* @date 2021/7/29
*/
@Getter
@AllArgsConstructor
public enum CouponStatusEnum {

    AVAIL(1, "有效"),
    EXPIRED(2, "失效(超出有效期)"),
    DISABLED(3, "系统禁用")
    ;
    private Integer code;
    private String desc;

    public static CouponStatusEnum getType(Integer type) {
        for (CouponStatusEnum value : CouponStatusEnum.values()) {
            if(value.code.equals(type)) {
                return value;
            }
        }
        return null;
    }
}