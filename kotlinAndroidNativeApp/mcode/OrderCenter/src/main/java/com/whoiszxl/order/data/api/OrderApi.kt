package com.whoiszxl.order.data.api

import com.whoiszxl.base.data.protocol.BaseResp
import com.whoiszxl.order.data.protocol.Order
import com.whoiszxl.order.data.protocol.OrderList
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST
import rx.Observable

/**
 * 訂單接口
 */
interface OrderApi {


    /**
     * 通过购物车id创建一个订单
     */
    @FormUrlEncoded
    @POST("/order/create")
    fun orderCreate(
            @Field("shippingId")shippingId:Int): Observable<BaseResp<String>>


    /**
     * 取消一个订单
     */
    @FormUrlEncoded
    @POST("/order/cancel")
    fun orderCancel(
            @Field("orderNo")orderNo:String): Observable<BaseResp<String>>


    /**
     * 获取订单详情
     */
    @FormUrlEncoded
    @POST("/order/detail")
    fun orderDetail(
            @Field("orderNo")orderNo:String): Observable<BaseResp<Order>>


    /**
     * 获取订单列表
     */
    @FormUrlEncoded
    @POST("/order/list")
    fun orderList(
            @Field("shippingId")shippingId:Int): Observable<BaseResp<OrderList>>


}