package com.whoiszxl.enums;

public enum PageIdEnum {

    home("首页"),
    category("分类页"),
    discovery("发现页"),
    cart("购物车页"),
    member("个人页"),
    product_list("商品列表页"),
    product_detail("商品详情页"),
    pay("支付页"),
    pay_result("支付结果页"),
    login("登录页"),
    register("注册页"),
    coupon_list("优惠券列表页"),
    seckill_list("秒杀列表页"),
    order_List("订单列表页")
    ;


    private String desc;
    PageIdEnum(String desc) {
        this.desc = desc;
    }
}
