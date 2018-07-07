package com.whoiszxl.goods.presenter

import android.widget.Toast
import com.whoiszxl.base.ext.execute
import com.whoiszxl.base.presenter.BasePresenter
import com.whoiszxl.base.rx.BaseSubscriber
import com.whoiszxl.base.utils.AppPrefsUtils
import com.whoiszxl.goods.data.protocol.Cart
import com.whoiszxl.goods.presenter.view.CartListView
import com.whoiszxl.goods.service.CartService
import com.whoiszxl.provider.common.ProviderConstant
import javax.inject.Inject

/**
 * 购物车Presenter
 */
class CartListPresenter @Inject constructor() : BasePresenter<CartListView>() {

    @Inject
    lateinit var cartService: CartService

    private var authToken = AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_SIGN)

    /**
     * 获取购物车列表
     */
    fun getCartList() {
        if (!checkNetWork()) return

        //需要获取到sp中的token
        cartService.getCartList(authToken).execute(object : BaseSubscriber<Cart?>(mView) {
            override fun onNext(t: Cart?) {
                mView.onGetCartListResult(t)
            }
        }, lifecycleProvider)

    }

    fun deleteCartList(productIds: String){
        if(!checkNetWork()) return
        cartService.deleteCartList(authToken, productIds).execute(object : BaseSubscriber<Cart>(mView) {
            override fun onNext(t: Cart) {
                mView.onDeleteCartListResult(true)
            }
        }, lifecycleProvider)
    }


    fun updateCartNum(count: Int, productId: Int) {
        if(!checkNetWork()) return

        cartService.updateCart(authToken, count, productId).execute(object : BaseSubscriber<Cart>(mView) {
            override fun onNext(t: Cart) {

            }
        }, lifecycleProvider)
    }


    /**
     * 选中单个商品
     */
    fun selectCartOne(productIds:Int) {
        if(!checkNetWork()) return

        cartService.selectCartOne(authToken, productIds).execute(object : BaseSubscriber<Cart>(mView) {
            override fun onNext(t: Cart) {
                mView.onSelectOrUnSelectResult(t.cartTotalPrice)
            }
        }, lifecycleProvider)

    }
    /**
     * 不选中单个商品
     */
    fun selectUnCartOne(productIds:Int){
        if(!checkNetWork()) return

        cartService.selectUnCartOne(authToken, productIds).execute(object : BaseSubscriber<Cart>(mView) {
            override fun onNext(t: Cart) {
                mView.onSelectOrUnSelectResult(t.cartTotalPrice)
            }
        }, lifecycleProvider)
    }


    /**
     * 选中所有商品
     */
    fun selectCartAll() {
        if(!checkNetWork()) return

        cartService.selectCartAll(authToken).execute(object : BaseSubscriber<Cart>(mView) {
            override fun onNext(t: Cart) {
                mView.onSelectOrUnSelectResult(t.cartTotalPrice)
            }
        }, lifecycleProvider)
    }

    /**
     * 不选中所有商品
     */
    fun selectUnCartAll() {
        if(!checkNetWork()) return

        cartService.selectUnCartAll(authToken).execute(object : BaseSubscriber<Cart>(mView) {
            override fun onNext(t: Cart) {
                mView.onSelectOrUnSelectResult(t.cartTotalPrice)
            }
        }, lifecycleProvider)
    }


}
