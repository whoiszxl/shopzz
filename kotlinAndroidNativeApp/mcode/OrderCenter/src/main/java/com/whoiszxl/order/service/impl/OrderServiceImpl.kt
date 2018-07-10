package com.whoiszxl.order.service.impl

import com.whoiszxl.base.ext.convert
import com.whoiszxl.base.ext.convertBoolean
import com.whoiszxl.order.data.protocol.Order
import com.whoiszxl.order.data.protocol.OrderList
import com.whoiszxl.order.data.repository.OrderRepository
import com.whoiszxl.order.service.OrderService
import rx.Observable
import javax.inject.Inject

/**
 *  订单业务实现类
 */
class OrderServiceImpl @Inject constructor(): OrderService {


    @Inject
    lateinit var repository: OrderRepository

    override fun orderCancel(authorization: String, orderNo: String): Observable<String> {
        return repository.orderCancel(authorization, orderNo).convert()
    }

    override fun orderDetail(authorization: String, orderNo: String): Observable<Order> {
        return repository.orderDetail(authorization, orderNo).convert()
    }

    override fun getOrderList(authorization: String, status: Int, pageSize: Int): Observable<OrderList> {
        return repository.orderList(authorization, status, pageSize).convert()
    }

    override fun submitOrder(authorization: String, shippingId: Int): Observable<Boolean> {
        return  repository.submitOrder(authorization, shippingId).convertBoolean()
    }

    override fun orderCartProduct(authorization: String): Observable<Order> {
        return repository.orderCartProduct(authorization).convert()
    }
}