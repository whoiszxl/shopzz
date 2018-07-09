package com.whoiszxl.order.presenter

import com.whoiszxl.base.ext.execute
import com.whoiszxl.base.presenter.BasePresenter
import com.whoiszxl.base.rx.BaseSubscriber
import com.whoiszxl.order.data.protocol.Order
import com.whoiszxl.order.presenter.view.OrderConfirmView
import com.whoiszxl.order.service.OrderService
import javax.inject.Inject

/**
 * 订单确认页 Presenter
 */
class OrderConfirmPresenter @Inject constructor() : BasePresenter<OrderConfirmView>() {

    @Inject
    lateinit var orderService: OrderService

    /**
     * 根据订单的编号获取到订单
     */
    fun getOrderById(orderNo: String) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()

        orderService.orderDetail(orderNo).execute(object : BaseSubscriber<Order>(mView) {
            override fun onNext(t: Order) {
                mView.onGetOrderByIdResult(t)
            }
        }, lifecycleProvider)
    }


    fun submitOrder(shippingId: Int) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        orderService.submitOrder(shippingId).execute(object : BaseSubscriber<Boolean>(mView) {
            override fun onNext(t: Boolean) {
                mView.onSubmitOrderResult(t)
            }
        }, lifecycleProvider)
    }


}