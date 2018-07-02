package com.whoiszxl.goods.ui.fragment

import com.whoiszxl.base.ui.fragment.BaseMvpFragment
import com.whoiszxl.goods.data.protocol.Goods
import com.whoiszxl.goods.presenter.GoodsDetailPresenter
import com.whoiszxl.goods.presenter.view.GoodsDetailView


/*
    商品详情Tab One
 */
class GoodsDetailTabOneFragment : BaseMvpFragment<GoodsDetailPresenter>(), GoodsDetailView {
    override fun injectComponent() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onGetGoodsDetailResult(result: Goods) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}