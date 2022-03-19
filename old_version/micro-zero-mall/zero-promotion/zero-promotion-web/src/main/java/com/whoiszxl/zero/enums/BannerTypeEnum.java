package com.whoiszxl.zero.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 轮播图类型
 * @author whoiszxl
 * @date 2021/3/17
 */
@Getter
@AllArgsConstructor
public enum BannerTypeEnum {

    PC_BANNER(0, "PC轮播图"),
    APP_BANNER(1, "APP轮播图"),
    APP_NAVIGATION(2, "APP导航小组件"),
    ;
    private Integer code;
    private String desc;
}