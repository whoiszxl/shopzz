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

    override fun orderCancel(orderNo: String): Observable<String> {
        return repository.orderCancel(orderNo).convert()
    }

    override fun orderDetail(orderNo: String): Observable<Order> {
        return repository.orderDetail(orderNo).convert()
    }

    override fun getOrderList(status: Int, pageSize: Int): Observable<OrderList> {
        return repository.getOrderList(status, pageSize).convert()
    }

    override fun submitOrder(shippingId: Int): Observable<Boolean> {
        return  repository.submitOrder(shippingId).convertBoolean()
    }

}