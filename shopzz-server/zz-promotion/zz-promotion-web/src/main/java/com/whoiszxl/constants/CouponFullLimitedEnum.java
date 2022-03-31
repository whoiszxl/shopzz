package com.whoiszxl.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
* 优惠券是否限制分类枚举
*
* @author whoiszxl
* @date 2021/7/29
*/
@Getter
@AllArgsConstructor
public enum CouponFullLimitedEnum {

    NOT_LIMIT(1, "全场通用"),
    LIMIT(2, "分类限制")
    ;
    private Integer code;
    private String desc;

    public static CouponFullLimitedEnum getType(Integer type) {
        for (CouponFullLimitedEnum value : CouponFullLimitedEnum.values()) {
            if(value.code.equals(type)) {
                return value;
            }
        }
        return null;
    }
}