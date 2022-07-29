package com.whoiszxl.enums;

public enum ActionIdEnum {

    swipe_left("上划"),
    swipe_right("下划"),
    cart_add("加购"),
    cart_sub("减购"),
    address_add("地址增加"),
    member_info_edit("信息修改"),
    search("搜索商品"),
    seckill("秒杀下单")
    ;


    String desc;

    ActionIdEnum(String desc) {
        this.desc = desc;
    }

}
