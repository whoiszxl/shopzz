package com.whoiszxl.order.data.repository

import com.whoiszxl.base.data.net.RetrofitFactory
import com.whoiszxl.base.data.protocol.BaseResp
import com.whoiszxl.order.data.api.OrderApi
import com.whoiszxl.order.data.protocol.Order
import com.whoiszxl.order.data.protocol.OrderList
import rx.Observable
import javax.inject.Inject

/**
 * 订单数据层
 */
class OrderRepository @Inject constructor() {

    /**
     * 取消订单
     */
    fun orderCancel(authorization: String, orderNo: String): Observable<BaseResp<String>> {
        return RetrofitFactory.instance.create(OrderApi::class.java).orderCancel(authorization, orderNo)
    }

    /**
     * 根据ID查询订单
     */
    fun orderDetail(authorization: String, orderNo: String): Observable<BaseResp<Order>> {
        return RetrofitFactory.instance.create(OrderApi::class.java).orderDetail(authorization, orderNo)
    }

    /**
     * 获取当前购物车中的选中商品
     */
    fun orderCartProduct(authorization: String): Observable<BaseResp<Order>> {
        return RetrofitFactory.instance.create(OrderApi::class.java).orderCartProduct(authorization)
    }

    /**
     * 根据状态查询订单列表
     */
    fun orderList(authorization: String, status: Int, pageSize: Int): Observable<BaseResp<OrderList>> {
        return RetrofitFactory.instance.create(OrderApi::class.java).orderList(authorization, status, pageSize)
    }

    /**
     * 提交订单
     */
    fun submitOrder(authorization: String, shippingId: Int): Observable<BaseResp<String>> {
        return RetrofitFactory.instance.create(OrderApi::class.java).orderCreate(authorization, shippingId)
    }

}