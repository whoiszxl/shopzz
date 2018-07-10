package com.whoiszxl.order.service

import com.whoiszxl.order.data.protocol.Order
import com.whoiszxl.order.data.protocol.OrderList
import rx.Observable

interface OrderService {
    /*
        取消订单
     */
    fun orderCancel(authorization: String, orderNo: String): Observable<String>

    /*
        根据ID查询订单
     */
    fun orderDetail(authorization: String, orderNo: String): Observable<Order>

    /*
        根据状态查询订单列表
     */
    fun getOrderList(authorization: String, status: Int,pageSize: Int): Observable<OrderList>
    /*
        提交订单
     */
    fun submitOrder(authorization: String, shippingId: Int): Observable<Boolean>


    fun orderCartProduct(authorization: String): Observable<Order>
}