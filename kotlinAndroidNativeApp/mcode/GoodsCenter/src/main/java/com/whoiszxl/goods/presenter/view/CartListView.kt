package com.whoiszxl.goods.presenter.view

import com.whoiszxl.base.presenter.view.BaseView
import com.whoiszxl.goods.data.protocol.Cart

/**
 * 购物车页面 视图回调
 */
interface CartListView : BaseView {

    //获取购物车列表
    fun onGetCartListResult(result: Cart?)
    //删除购物车
    fun onDeleteCartListResult(result: Boolean)
    //提交购物车
    fun onSubmitCartListResult(result: Int)

    fun onSelectOrUnSelectResult(result: Long)
}
