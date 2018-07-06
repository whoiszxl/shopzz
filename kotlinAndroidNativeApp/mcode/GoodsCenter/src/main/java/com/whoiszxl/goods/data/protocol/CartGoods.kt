package com.whoiszxl.goods.data.protocol

/**
 * 购物车中实际的商品实体
 */
data class CartGoods(
        val id: Int,//购物车ID
        val limitQuantity: String,//是否还有库存：LIMIT_NUM_SUCCESS and LIMIT_NUM_FAIL
        var productChecked: Int,//是否选中, 1选中   0未选中
        var productCheckedBoolean: Boolean, //是否选中 Boolean
        val productId:Int,//具体商品ID
        val productMainImage: String,//商品图片
        val productName: String,//商品名称
        val productPrice: Long,//商品单价
        var quantity: Int,//商品数量
        val productTotalPrice:Long,//商品总价
        var userId:Long,//购买的用户id
        val imageHost: String//图片网址
)
