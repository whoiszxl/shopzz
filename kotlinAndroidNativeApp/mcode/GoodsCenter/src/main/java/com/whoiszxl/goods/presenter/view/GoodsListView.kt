package com.whoiszxl.goods.presenter.view

import com.whoiszxl.base.presenter.view.BaseView
import com.whoiszxl.goods.data.protocol.GoodsList

/**
 * 商品列表 视图回调
 */
interface GoodsListView : BaseView {

    //获取商品列表
    fun onGetGoodsListResult(result: GoodsList)
}