package com.whoiszxl.order.data.repository

import com.whoiszxl.base.data.net.RetrofitFactory
import com.whoiszxl.base.data.protocol.BaseResp
import com.whoiszxl.order.data.api.OrderApi
import com.whoiszxl.order.data.protocol.Order
import rx.Observable
import javax.inject.Inject

/**
 * 订单数据层
 */
class OrderRepository @Inject constructor() {

    /*
        取消订单
     */
    fun orderCancel(orderNo: String): Observable<BaseResp<String>> {
        return RetrofitFactory.instance.create(OrderApi::class.java).orderCancel(orderNo)
    }

    /*
        根据ID查询订单
     */
    fun orderDetail(orderNo: String): Observable<BaseResp<Order>> {
        return RetrofitFactory.instance.create(OrderApi::class.java).orderDetail(orderNo)
    }

    /*
        根据状态查询订单列表
     */
    fun getOrderList(orderStatus: Int): Observable<BaseResp<MutableList<Order>?>> {
        return RetrofitFactory.instance.create(OrderApi::class.java).getOrderList(GetOrderListReq(orderStatus))
    }

    /*
        提交订单
     */
    fun submitOrder(order: Order): Observable<BaseResp<String>> {
        return RetrofitFactory.instance.create(OrderApi::class.java).submitOrder(SubmitOrderReq(order))
    }

}