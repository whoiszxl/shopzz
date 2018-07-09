package com.whoiszxl.order.presenter.view

import com.whoiszxl.base.presenter.view.BaseView
import com.whoiszxl.order.data.protocol.Order

/**
 * 订单详情页 视图回调
 */
interface OrderDetailView : BaseView {

    fun onGetOrderByIdResult(result: Order)
}