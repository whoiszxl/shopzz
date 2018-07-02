package com.whoiszxl.goods.data.repository

import com.whoiszxl.base.data.net.RetrofitFactory
import com.whoiszxl.base.data.protocol.BaseResp
import com.whoiszxl.goods.data.api.CategoryApi
import com.whoiszxl.goods.data.protocol.Category
import rx.Observable
import javax.inject.Inject

/*
    商品分类 数据层
 */
class CategoryRepository @Inject constructor(){
    /*
        获取商品分类
     */
    fun getCategory(parentId: Int): Observable<BaseResp<MutableList<Category>?>> {
        return RetrofitFactory.instance.create(CategoryApi::class.java).getCategory(parentId)
    }

}
