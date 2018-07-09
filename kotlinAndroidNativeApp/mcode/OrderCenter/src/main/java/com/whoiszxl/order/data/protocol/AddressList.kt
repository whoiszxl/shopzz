package com.whoiszxl.order.data.protocol

/**
 * 订单列表s
 */
data class AddressList (
        val pageSize: Int,
        val total: Int,
        val list: MutableList<ShipAddress>
)