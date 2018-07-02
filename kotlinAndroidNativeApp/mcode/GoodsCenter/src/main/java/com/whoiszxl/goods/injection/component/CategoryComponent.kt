package com.whoiszxl.goods.injection.component

import com.whoiszxl.base.injection.PerComponentScope
import com.whoiszxl.base.injection.component.ActivityComponent
import com.whoiszxl.goods.injection.module.CategoryModule
import com.whoiszxl.goods.ui.fragment.CategoryFragment
import dagger.Component

/**
 * 商品分类Component
 */
@PerComponentScope
@Component(dependencies = arrayOf(ActivityComponent::class),modules = arrayOf(CategoryModule::class))
interface CategoryComponent {
    fun inject(fragment: CategoryFragment)
}
