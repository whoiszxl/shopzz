package com.whoiszxl.order.data.protocol

/**
 * 订单中的商品
 */
data class OrderGoods(
        val productId: Int,
        var orderNo: String,
        val currentUnitPrice: Long,
        val productImage: String,
        val productName: String,
        val quantity: Int,
        val totalPrice: Long,
        val createTime: String
)