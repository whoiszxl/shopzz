package com.whoiszxl.order.data.api

import com.whoiszxl.base.data.protocol.BaseResp
import com.whoiszxl.order.data.protocol.AddressList
import com.whoiszxl.order.data.protocol.ShipAddress
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST
import rx.Observable

/**
 * 地址管理
 */
interface ShipAddressApi {

    /**
     * 添加收货地址
     */
    @FormUrlEncoded
    @POST("/shipping/add")
    fun addressAdd(
            @Header("Authorization") authorization: String,
            @Field("receiverName")receiverName:String,
            @Field("receiverProvince")receiverProvince:String,
            @Field("receiverCity")receiverCity:String,
            @Field("receiverAddress")receiverAddress:String,
            @Field("receiverPhone")receiverPhone:String,
            @Field("receiverZip")receiverZip:String): Observable<BaseResp<String>>

    /**
     * 删除收货地址
     */
    @FormUrlEncoded
    @POST("/shipping/delete")
    fun addressDelete(
            @Header("Authorization") authorization: String,
            @Field("shippingId")shippingId:Int
    ): Observable<BaseResp<String>>

    /**
     * 修改收货地址
     */
    @FormUrlEncoded
    @POST("/shipping/update")
    fun addressUpdate(
            @Header("Authorization") authorization: String,
            @Field("receiverName")receiverName:String,
            @Field("receiverProvince")receiverProvince:String,
            @Field("receiverCity")receiverCity:String,
            @Field("receiverAddress")receiverAddress:String,
            @Field("receiverPhone")receiverPhone:String,
            @Field("receiverZip")receiverZip:String,
            @Field("id")id:String): Observable<BaseResp<String>>

    /**
     * 查询收货地址列表
     */
    @FormUrlEncoded
    @POST("/shipping/list")
    fun addressList(
            @Header("Authorization") authorization: String,
            @Field("pageSize")pageSize:Int
    ): Observable<BaseResp<MutableList<AddressList>?>>


    /**
     * 通过id查询地址
     */
    @FormUrlEncoded
    @POST("/shipping/selects")
    fun addressSelects(
            @Header("Authorization") authorization: String,
            @Field("shippingId")shippingId:Int
    ): Observable<BaseResp<ShipAddress>>

}