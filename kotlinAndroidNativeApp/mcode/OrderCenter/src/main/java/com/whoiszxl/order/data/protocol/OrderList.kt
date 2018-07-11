package com.whoiszxl.order.data.protocol

/**
 * 订单列表s
 */
data class OrderList (
    val pageSize: Int,
    val total: Int,
    val list: MutableList<Order>?
)