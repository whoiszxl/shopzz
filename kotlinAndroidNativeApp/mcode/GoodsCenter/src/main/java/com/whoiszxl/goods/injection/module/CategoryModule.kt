package com.whoiszxl.goods.injection.module

import com.whoiszxl.goods.service.CategoryService
import com.whoiszxl.goods.service.impl.CategoryServiceImpl
import dagger.Module
import dagger.Provides

/*
    商品分类Module
 */
@Module
class CategoryModule {

    @Provides
    fun provideCategoryService(categoryService: CategoryServiceImpl): CategoryService {
        return categoryService
    }

}