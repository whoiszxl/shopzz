package com.whoiszxl.goods.service

import com.whoiszxl.goods.data.protocol.Category
import rx.Observable

/**
 * 商品分类业务层接口
 */
interface CategoryService {

    /*
        获取分类
     */
    fun getCategory(parentId:Int): Observable<MutableList<Category>?>
}
