package com.whoiszxl.order.service

import com.whoiszxl.order.data.protocol.Order
import com.whoiszxl.order.data.protocol.OrderList
import rx.Observable

interface OrderService {
    /*
        取消订单
     */
    fun orderCancel(orderNo: String): Observable<String>

    /*
        根据ID查询订单
     */
    fun orderDetail(orderNo: String): Observable<Order>

    /*
        根据状态查询订单列表
     */
    fun getOrderList(status: Int,pageSize: Int): Observable<OrderList>
    /*
        提交订单
     */
    fun submitOrder(shippingId: Int): Observable<String>
}