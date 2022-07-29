package com.whoiszxl.enums;

public enum ItemTypeEnum {

    spu_id("spu id"),
    sku_id("sku id"),
    keyword("搜索关键词"),
    coupon_id("优惠券ID");


    String desc;

    ItemTypeEnum(String desc) {
        this.desc = desc;
    }

}
