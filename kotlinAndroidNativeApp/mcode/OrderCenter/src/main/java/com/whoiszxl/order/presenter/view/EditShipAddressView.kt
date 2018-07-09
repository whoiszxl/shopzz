package com.whoiszxl.order.presenter.view

import com.whoiszxl.base.presenter.view.BaseView

/**
 * 编辑收货人信息 视图回调
 */
interface EditShipAddressView : BaseView {
    //添加收货人回调
    fun onAddShipAddressResult(result: Boolean)
    //修改收货人回调
    fun onEditShipAddressResult(result: Boolean)
}
