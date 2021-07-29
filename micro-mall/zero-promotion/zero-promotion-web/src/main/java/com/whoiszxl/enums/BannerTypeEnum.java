package com.whoiszxl.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
* TODO
*
* @author zhouxiaolong
* @date 2021/7/29
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