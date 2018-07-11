package com.whoiszxl.order.presenter

import com.whoiszxl.base.common.BaseConstant
import com.whoiszxl.base.ext.execute
import com.whoiszxl.base.presenter.BasePresenter
import com.whoiszxl.base.rx.BaseSubscriber
import com.whoiszxl.base.utils.AppPrefsUtils
import com.whoiszxl.order.data.protocol.Order
import com.whoiszxl.order.data.protocol.OrderList
import com.whoiszxl.order.presenter.view.OrderListView
import com.whoiszxl.order.service.OrderService
import com.whoiszxl.provider.common.ProviderConstant
import javax.inject.Inject

class OrderListPresenter @Inject constructor() : BasePresenter<OrderListView>() {
    @Inject
    lateinit var orderService: OrderService

    private var authToken = AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_SIGN)
    /*
        根据订单状态获取订单列表
     */
    fun getOrderList(orderStatus: Int) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        orderService.getOrderList(authToken, orderStatus, BaseConstant.ORDER_SHOW_COUNT).execute(object : BaseSubscriber<OrderList>(mView) {
            override fun onNext(t: OrderList) {
                mView.onGetOrderListResult(t)
            }
        }, lifecycleProvider)

    }

    /*
        订单确认收货
     */
    fun confirmOrder(orderId: Int) {
//        if (!checkNetWork()) {
//            return
//        }
//        mView.showLoading()
//        orderService.confirmOrder(orderId).excute(object : BaseSubscriber<Boolean>(mView) {
//            override fun onNext(t: Boolean) {
//                mView.onConfirmOrderResult(t)
//            }
//        }, lifecycleProvider)

    }

    /*
        取消订单
     */
    fun cancelOrder(orderNo: String) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        orderService.orderCancel(authToken, orderNo).execute(object : BaseSubscriber<Boolean>(mView) {
            override fun onNext(t: Boolean) {
                mView.onCancelOrderResult(t)
            }
        }, lifecycleProvider)

    }
}