package com.whoiszxl.enums.promotion;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
* 轮播图类型枚举
*
* @author whoiszxl
* @date 2021/7/29
*/
@Getter
@AllArgsConstructor
public enum BannerTypeEnum {

    PC_BANNER(0, "PC轮播图"),
    APP_BANNER(1, "APP轮播图"),
    APP_NAVIGATION(2, "APP导航小组件"),
    APP_SMALL_BANNER(3, "APP通栏轮播图"),
    ;
    private Integer code;
    private String desc;

    public static BannerTypeEnum getType(Integer type) {
        for (BannerTypeEnum value : BannerTypeEnum.values()) {
            if(value.code.equals(type)) {
                return value;
            }
        }
        return null;
    }
}