package com.whoiszxl.goods.injection.component

import com.whoiszxl.base.injection.PerComponentScope
import com.whoiszxl.base.injection.component.ActivityComponent
import com.whoiszxl.goods.injection.module.GoodsModule
import com.whoiszxl.goods.ui.activity.GoodsActivity
import com.whoiszxl.goods.ui.fragment.GoodsDetailTabOneFragment
import dagger.Component

/**
 * 商品Component
 */
@PerComponentScope
@Component(dependencies = arrayOf(ActivityComponent::class),modules = arrayOf(GoodsModule::class))
interface GoodsComponent {
    fun inject(activity: GoodsActivity)
    fun inject(fragment: GoodsDetailTabOneFragment)
}
