package com.whoiszxl.mall.injection.component

import com.whoiszxl.base.injection.PerComponentScope
import com.whoiszxl.base.injection.component.ActivityComponent
import com.whoiszxl.mall.injection.module.HomeModule
import com.whoiszxl.mall.ui.fragment.HomeFragment
import dagger.Component

@PerComponentScope
@Component(dependencies = arrayOf(ActivityComponent::class),modules = arrayOf(HomeModule::class))
interface HomeComponent {
    fun inject(fragment: HomeFragment)
}