package com.whoiszxl.goods.service.impl

import com.whoiszxl.base.ext.convert
import com.whoiszxl.goods.data.protocol.Cart
import com.whoiszxl.goods.data.repository.CartRepository
import com.whoiszxl.goods.service.CartService
import rx.Observable
import javax.inject.Inject

class CartServiceImpl @Inject constructor() : CartService {

    @Inject
    lateinit var repository: CartRepository

    override fun getCartList(authorization: String): Observable<Cart?> {
        return repository.getCartList(authorization).convert()
    }

    override fun addCart(authorization: String, count: Int, productId: Int): Observable<Cart> {
        return repository.addCart(authorization, count, productId).convert()
    }

    override fun deleteCartList(authorization: String, productIds: String): Observable<Cart> {
        return repository.deleteCartList(authorization, productIds).convert()
    }

    override fun selectCartOne(authorization: String, productId: Int): Observable<Cart> {
        return repository.selectCartOne(authorization, productId).convert()
    }

    override fun selectUnCartOne(authorization: String, productId: Int): Observable<Cart> {
        return repository.selectUnCartOne(authorization, productId).convert()
    }

    override fun selectCartAll(authorization: String): Observable<Cart> {
        return repository.selectCartAll(authorization).convert()
    }

    override fun selectUnCartAll(authorization: String): Observable<Cart> {
        return repository.selectUnCartAll(authorization).convert()
    }

    override fun updateCart(authorization: String, count: Int, productId: Int): Observable<Cart> {
        return repository.updateCart(authorization, count, productId).convert()
    }
}