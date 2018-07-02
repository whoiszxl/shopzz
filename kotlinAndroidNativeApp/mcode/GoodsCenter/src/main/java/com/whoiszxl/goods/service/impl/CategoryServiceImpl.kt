package com.whoiszxl.goods.service.impl

import com.whoiszxl.base.ext.convert
import com.whoiszxl.goods.data.protocol.Category
import com.whoiszxl.goods.data.repository.CategoryRepository
import com.whoiszxl.goods.service.CategoryService
import rx.Observable
import javax.inject.Inject

/**
 * 商品分类 业务层 实现类
 */
class CategoryServiceImpl @Inject constructor(): CategoryService {
    @Inject
    lateinit var repository: CategoryRepository

    /*
        获取商品分类列表
     */
    override fun getCategory(parentId: Int): Observable<MutableList<Category>?> {
        return repository.getCategory(parentId).convert()
    }


}