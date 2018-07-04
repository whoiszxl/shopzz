package com.whoiszxl.goods.data.repository

import com.whoiszxl.base.data.net.RetrofitFactory
import com.whoiszxl.base.data.protocol.BaseResp
import com.whoiszxl.goods.data.api.CartApi
import com.whoiszxl.goods.data.protocol.Cart
import rx.Observable
import javax.inject.Inject

/**
 * 购物车数据层
 */
class CartRepository  @Inject constructor(){
    /**
     * 购物车列表
     */
    fun getCartList(authorization: String): Observable<BaseResp<Cart?>> {
        return RetrofitFactory.instance.create(CartApi::class.java).getCartList(authorization)
    }

    /**
     * 通过商品id添加到购物车
     */
    fun addCart(authorization: String,count:Int,productId:Int): Observable<BaseResp<Cart>> {
        return RetrofitFactory.instance.create(CartApi::class.java).addCart(authorization, count, productId)
    }

    /**
     * 删除购物车的商品 通过逗号分隔的商品id字符串
     */
    fun deleteCartList(authorization: String,productIds:String): Observable<BaseResp<Cart>> {
        return RetrofitFactory.instance.create(CartApi::class.java).deleteCartList(authorization, productIds)
    }

    /**
     * 选中单个商品
     */
    fun selectCartOne(authorization: String, productIds:Int): Observable<BaseResp<Cart>> {
        return RetrofitFactory.instance.create(CartApi::class.java).selectCartOne(authorization, productIds)
    }

    /**
     * 不选中单个商品
     */
    fun selectUnCartOne(authorization: String,productIds:Int): Observable<BaseResp<Cart>> {
        return RetrofitFactory.instance.create(CartApi::class.java).selectUnCartOne(authorization, productIds)
    }


    /**
     * 选中所有商品
     */
    fun selectCartAll(authorization: String): Observable<BaseResp<Cart>> {
        return RetrofitFactory.instance.create(CartApi::class.java).selectCartAll(authorization)
    }

    /**
     * 不选中所有商品
     */
    fun selectUnCartAll(authorization: String): Observable<BaseResp<Cart>> {
        return RetrofitFactory.instance.create(CartApi::class.java).selectUnCartAll(authorization)
    }


    /**
     * 更新购物车的商品
     */
    fun updateCart(authorization: String,count:Int,productId:Int): Observable<BaseResp<Cart>> {
        return RetrofitFactory.instance.create(CartApi::class.java).updateCart(authorization, count, productId)
    }


}