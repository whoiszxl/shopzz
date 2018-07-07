package com.whoiszxl.goods.data.api

import com.whoiszxl.base.data.protocol.BaseResp
import com.whoiszxl.goods.data.protocol.Cart
import retrofit2.http.*
import rx.Observable

interface CartApi {

    /**
     * 购物车列表
     */
    @GET("/cart/list")
    fun getCartList(
            @Header("Authorization") authorization: String
    ): Observable<BaseResp<Cart?>>

    /**
     * 通过商品id添加到购物车
     */
    @FormUrlEncoded
    @POST("/cart/add")
    fun addCart(
            @Header("Authorization") authorization: String,
            @Field("count")count:Int,
            @Field("productId")productId:Int): Observable<BaseResp<Cart>>

    /**
     * 删除购物车的商品 通过逗号分隔的商品id字符串
     */
    @FormUrlEncoded
    @POST("/cart/delete_product")
    fun deleteCartList(
            @Header("Authorization") authorization: String,
            @Field("productIds") productIds:String
    ): Observable<BaseResp<Cart>>

    /**
     * 选中单个商品
     */
    @FormUrlEncoded
    @POST("/cart/select")
    fun selectCartOne(
            @Header("Authorization") authorization: String,
            @Field("productId") productIds:Int
    ): Observable<BaseResp<Cart>>

    /**
     * 不选中单个商品
     */
    @FormUrlEncoded
    @POST("/cart/un_select")
    fun selectUnCartOne(
            @Header("Authorization") authorization: String,
            @Field("productId") productIds:Int
    ): Observable<BaseResp<Cart>>


    /**
     * 选中所有商品
     */
    @POST("/cart/select_all")
    fun selectCartAll(
            @Header("Authorization") authorization: String
    ): Observable<BaseResp<Cart>>

    /**
     * 不选中所有商品
     */
    @POST("/cart/un_select_all")
    fun selectUnCartAll(
            @Header("Authorization") authorization: String
    ): Observable<BaseResp<Cart>>


    /**
     * 更新购物车的商品
     */
    @FormUrlEncoded
    @POST("/cart/update")
    fun updateCart(
            @Header("Authorization") authorization: String,
            @Field("count")count:Int,
            @Field("productId")productId:Int): Observable<BaseResp<Cart>>


    @FormUrlEncoded
    @POST("/order/create")
    fun createOrder(
            @Header("Authorization") authorization: String,
            @Field("shippingId")shippingId:Int
    ): Observable<BaseResp<String>>
}