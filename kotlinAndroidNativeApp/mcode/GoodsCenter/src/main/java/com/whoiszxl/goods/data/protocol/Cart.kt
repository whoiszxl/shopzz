package com.whoiszxl.goods.data.protocol

/**
 * 购物车最外层的包装类
 */
data class Cart(
        var allChecked: Boolean, //是否全部选中
        var cartProductVoList: MutableList<CartGoods>, //购物车中的商品集合
        var cartTotalPrice: Long, //购物车中的总价
        val imageHost: String //图片host地址
)

