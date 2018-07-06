package com.whoiszxl.goods.presenter

import com.whoiszxl.base.ext.execute
import com.whoiszxl.base.presenter.BasePresenter
import com.whoiszxl.base.rx.BaseSubscriber
import com.whoiszxl.base.utils.AppPrefsUtils
import com.whoiszxl.goods.common.GoodsConstant
import com.whoiszxl.goods.data.protocol.Cart
import com.whoiszxl.goods.data.protocol.Goods
import com.whoiszxl.goods.presenter.view.GoodsDetailView
import com.whoiszxl.goods.service.CartService
import com.whoiszxl.goods.service.GoodsService
import com.whoiszxl.provider.common.ProviderConstant
import javax.inject.Inject

/*
    商品详情 Presenter
 */
class GoodsDetailPresenter @Inject constructor() : BasePresenter<GoodsDetailView>() {

    @Inject
    lateinit var goodsService: GoodsService

    @Inject
    lateinit var cartService: CartService

    private var authToken = AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_SIGN)
    /*
        获取商品详情
     */
    fun getGoodsDetailList(goodsId: Int) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        goodsService.getGoodsDetail(goodsId).execute(object : BaseSubscriber<Goods>(mView) {
            override fun onNext(t: Goods) {
                mView.onGetGoodsDetailResult(t)
            }
        }, lifecycleProvider)

    }



    fun addCart(count: Int, productId: Int) {
        if (!checkNetWork()) {
            return
        }
        cartService.addCart(authToken, count, productId).execute(object : BaseSubscriber<Cart>(mView) {
            override fun onNext(t: Cart) {
                AppPrefsUtils.putInt(GoodsConstant.SP_CART_SIZE,t.cartProductVoList.size)
                mView.onAddCartResult(t.cartProductVoList.size)
            }
        }, lifecycleProvider)
    }

}