package com.whoiszxl.order.presenter

import com.whoiszxl.base.ext.execute
import com.whoiszxl.base.presenter.BasePresenter
import com.whoiszxl.base.rx.BaseSubscriber
import com.whoiszxl.base.utils.AppPrefsUtils
import com.whoiszxl.order.data.protocol.Order
import com.whoiszxl.order.presenter.view.OrderConfirmView
import com.whoiszxl.order.service.OrderService
import com.whoiszxl.provider.common.ProviderConstant
import javax.inject.Inject

/**
 * 订单确认页 Presenter
 */
class OrderConfirmPresenter @Inject constructor() : BasePresenter<OrderConfirmView>() {

    @Inject
    lateinit var orderService: OrderService

    private var authToken = AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_SIGN)

    /**
     * 获取购物车中选中的提交订单商品
     */
    fun getOrderCheckProduct() {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()

        orderService.orderCartProduct(authToken).execute(object : BaseSubscriber<Order>(mView) {
            override fun onNext(t: Order) {
                mView.onGetOrderCartProductResult(t)
            }
        }, lifecycleProvider)
    }


    fun submitOrder(shippingId: Int) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        orderService.submitOrder(authToken, shippingId).execute(object : BaseSubscriber<Boolean>(mView) {
            override fun onNext(t: Boolean) {
                mView.onSubmitOrderResult(t)
            }
        }, lifecycleProvider)
    }


}