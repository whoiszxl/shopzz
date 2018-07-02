package com.whoiszxl.goods.data.repository

import com.whoiszxl.base.data.net.RetrofitFactory
import com.whoiszxl.base.data.protocol.BaseResp
import com.whoiszxl.goods.data.api.GoodsApi
import com.whoiszxl.goods.data.protocol.Goods
import com.whoiszxl.goods.data.protocol.GoodsList
import rx.Observable
import javax.inject.Inject

/*
    商品数据层
 */
class GoodsRepository @Inject constructor() {

    /*
        根据分类搜索商品
     */
    fun getGoodsList(categoryId: Int, pageNo: Int): Observable<BaseResp<GoodsList>> {
        return RetrofitFactory.instance.create(GoodsApi::class.java).getGoodsList(categoryId, pageNo)
    }

    /*
        根据关键字搜索商品
     */
    fun getGoodsListByKeyword(keyword: String, pageNo: Int): Observable<BaseResp<GoodsList>> {
        return RetrofitFactory.instance.create(GoodsApi::class.java).getGoodsListByKeyword(keyword, pageNo)
    }

    /*
        商品详情
     */
    fun getGoodsDetail(goodsId: Int): Observable<BaseResp<Goods>> {
        return RetrofitFactory.instance.create(GoodsApi::class.java).getGoodsDetail(goodsId)
    }
}
