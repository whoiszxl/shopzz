package com.whoiszxl.order.data.api

import com.whoiszxl.base.data.protocol.BaseResp
import com.whoiszxl.order.data.protocol.Order
import com.whoiszxl.order.data.protocol.OrderList
import retrofit2.http.*
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
            @Header("Authorization") authorization: String,
            @Field("shippingId")shippingId:Int): Observable<BaseResp<String>>


    /**
     * 取消一个订单
     */
    @FormUrlEncoded
    @POST("/order/cancel")
    fun orderCancel(
            @Header("Authorization") authorization: String,
            @Field("orderNo")orderNo:String): Observable<BaseResp<String>>


    /**
     * 获取当前购物车中的选中商品
     */
    @POST("/order/get_order_cart_product")
    fun orderCartProduct(
            @Header("Authorization") authorization: String
    ): Observable<BaseResp<Order>>

    /**
     * 获取订单详情
     */
    @GET("/order/detail")
    fun orderDetail(
            @Header("Authorization") authorization: String,
            @Query("orderNo")orderNo:String): Observable<BaseResp<Order>>


    /**
     * 获取订单列表
     */
    @GET("/order/list")
    fun orderList(
            @Header("Authorization") authorization: String,
            @Query("status")status:Int,
            @Query("pageSize")pageSize:Int): Observable<BaseResp<OrderList>>


}