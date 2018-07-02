package com.whoiszxl.goods.data.api

import com.whoiszxl.base.data.protocol.BaseResp
import com.whoiszxl.goods.data.protocol.Goods
import com.whoiszxl.goods.data.protocol.GoodsList
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

/**
 * 商品接口
 */
interface GoodsApi {

    /**
     * 获取商品列表
     */
    @GET("/product/list")
    fun getGoodsList(
            @Query("categoryId") categoryId:Int,
            @Query("pageNum") pageNum:Int,
            @Query("keyword") keyword:String = "",
            @Query("orderBy") orderBy:String = "default",
            @Query("pageSize") pageSize:String = "8"
    ): Observable<BaseResp<GoodsList>>

    /**
     * 通过关键字获取商品列表
     */
    @GET("/product/list")
    fun getGoodsListByKeyword(
            @Query("keyword") keyword:String = "",
            @Query("pageNum") pageNum:Int,
            @Query("orderBy") orderBy:String = "default",
            @Query("pageSize") pageSize:String = "8"
    ): Observable<BaseResp<GoodsList>>
    /**
     * 获取商品详情
     */
    @GET("/product/detail")
    fun getGoodsDetail(@Query("productId") productId:Int): Observable<BaseResp<Goods>>
}
