package com.whoiszxl.goods.service

import com.whoiszxl.goods.data.protocol.Cart
import rx.Observable

/**
 * 购物车服务接口
 */
interface CartService {
    /**
     * 购物车列表
     */
    fun getCartList(authorization: String): Observable<Cart?>

    /**
     * 通过商品id添加到购物车
     */
    fun addCart(authorization: String,count:Int,productId:Int): Observable<Cart>

    /**
     * 删除购物车的商品 通过逗号分隔的商品id字符串
     */
    fun deleteCartList(authorization: String,productIds:String): Observable<Cart>

    /**
     * 选中单个商品
     */
    fun selectCartOne(authorization: String, productIds:Int): Observable<Cart>

    /**
     * 不选中单个商品
     */
    fun selectUnCartOne(authorization: String,productIds:Int): Observable<Cart>


    /**
     * 选中所有商品
     */
    fun selectCartAll(authorization: String): Observable<Cart>

    /**
     * 不选中所有商品
     */
    fun selectUnCartAll(authorization: String): Observable<Cart>


    /**
     * 更新购物车的商品
     */
    fun updateCart(authorization: String,count:Int,productId:Int): Observable<Cart>
}