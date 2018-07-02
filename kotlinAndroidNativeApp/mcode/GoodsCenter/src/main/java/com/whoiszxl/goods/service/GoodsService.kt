package com.whoiszxl.goods.service

import com.whoiszxl.goods.data.protocol.Goods
import com.whoiszxl.goods.data.protocol.GoodsList
import rx.Observable

/**
 * 商品 业务层 接口
 */
interface GoodsService {

    /*
        获取商品列表
     */
    fun getGoodsList(categoryId: Int, pageNo: Int): Observable<GoodsList>

    /*
        根据关键字查询商品
     */
    fun getGoodsListByKeyword(keyword: String, pageNo: Int): Observable<GoodsList>

    /*
        获取商品详情
     */
    fun getGoodsDetail(goodsId: Int): Observable<Goods>
}
