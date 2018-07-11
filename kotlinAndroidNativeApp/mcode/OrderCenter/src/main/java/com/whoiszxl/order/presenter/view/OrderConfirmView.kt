package com.whoiszxl.order.presenter.view

import com.whoiszxl.base.presenter.view.BaseView
import com.whoiszxl.order.data.protocol.Order

/**
 * 订单确认页 视图回调
 */
interface OrderConfirmView : BaseView {

    //获取订单回调
    fun onGetOrderCartProductResult(result: Order)
    //提交订单回调
    fun onSubmitOrderResult(result:Boolean)
}
