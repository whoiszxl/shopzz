package com.whoiszxl.goods.data.api

import com.whoiszxl.base.data.protocol.BaseResp
import com.whoiszxl.goods.data.protocol.Category
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

/*
    商品分类接口
 */
interface CategoryApi {
    /*
        获取商品分类列表
     */
    @GET("/category/get_category")
    fun getCategory(@Query("categoryId") parentId:Int): Observable<BaseResp<MutableList<Category>?>>

}
