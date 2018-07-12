package com.whoiszxl.order.presenter

import com.whoiszxl.base.ext.execute
import com.whoiszxl.base.presenter.BasePresenter
import com.whoiszxl.base.rx.BaseSubscriber
import com.whoiszxl.base.utils.AppPrefsUtils
import com.whoiszxl.order.data.protocol.Order
import com.whoiszxl.order.presenter.view.OrderDetailView
import com.whoiszxl.order.service.OrderService
import com.whoiszxl.provider.common.ProviderConstant
import javax.inject.Inject

/**
 * 订单详情页Presenter
 */
class OrderDetailPresenter @Inject constructor() : BasePresenter<OrderDetailView>() {

    @Inject
    lateinit var orderService: OrderService

    private var authToken = AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_SIGN)
    /*
        根据ID查询订单
     */
    fun getOrderByOrderNo(orderNo: String) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        orderService.orderDetail(authToken, orderNo).execute(object : BaseSubscriber<Order>(mView) {
            override fun onNext(t: Order) {
                mView.onGetOrderByIdResult(t)
            }
        }, lifecycleProvider)

    }

}
