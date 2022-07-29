package com.whoiszxl.enums;

public enum DisplayTypeEnum {

    seckill("秒杀"),
    search("搜索"),
    recommend("推荐");


    String desc;

    DisplayTypeEnum(String desc) {
        this.desc = desc;
    }

}
