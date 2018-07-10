package com.whoiszxl.order.data.protocol

/**
 * 订单数据类
 */
data class Order(
        val orderNo : String,
        val paymentType: Int,
        val paymentTypeDesc: String,
        val postage: Long,
        val createTime: String,
        val imageHost: String,
        val payment: Long,
        val receiverName: String,
        val shippingId: Int,
        var shippingVo: ShipAddress?,
        val status: Int,
        var statusDesc: String,
        var productTotalPrice: Long,
        val orderItemVoList: MutableList<OrderGoods>
)