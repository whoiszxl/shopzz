package com.whoiszxl.goods.presenter

import com.whoiszxl.base.ext.execute
import com.whoiszxl.base.presenter.BasePresenter
import com.whoiszxl.base.rx.BaseSubscriber
import com.whoiszxl.goods.data.protocol.GoodsList
import com.whoiszxl.goods.presenter.view.GoodsListView
import com.whoiszxl.goods.service.GoodsService
import javax.inject.Inject

/**
 * 商品列表 Presenter
 */
class GoodsListPresenter @Inject constructor(): BasePresenter<GoodsListView>(){

    @Inject
    lateinit var goodsService:GoodsService

    /*
        获取商品列表
     */
    fun getGoodsList(categoryId: Int, pageNo: Int) {
        if (!checkNetWork()) {
            return
        }
        goodsService.getGoodsList(categoryId,pageNo).execute(object : BaseSubscriber<GoodsList>(mView) {
            override fun onNext(t: GoodsList) {
                mView.onGetGoodsListResult(t)
            }
        }, lifecycleProvider)

    }

    /*
        根据关键字 搜索商品
     */
    fun getGoodsListByKeyword(keyword: String, pageNo: Int) {
        if (!checkNetWork()) {
            return
        }
        goodsService.getGoodsListByKeyword(keyword,pageNo).execute(object : BaseSubscriber<GoodsList>(mView) {
            override fun onNext(t: GoodsList) {
                mView.onGetGoodsListResult(t)
            }
        }, lifecycleProvider)

    }

}