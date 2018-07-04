package com.whoiszxl.goods.presenter

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


    /*
        获取购物车列表
     */
    fun getCartList() {
        if (!checkNetWork()) {
            return
        }
        //需要获取到sp中的token
        val auth_token = AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_SIGN)
        cartService.getCartList(auth_token).execute(object : BaseSubscriber<Cart?>(mView) {
            override fun onNext(t: Cart?) {
                mView.onGetCartListResult(t)
            }
        }, lifecycleProvider)

    }
}
