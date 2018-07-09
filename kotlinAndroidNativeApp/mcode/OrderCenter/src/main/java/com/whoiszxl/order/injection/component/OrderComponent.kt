package com.whoiszxl.order.injection.component

import com.whoiszxl.base.injection.PerComponentScope
import com.whoiszxl.base.injection.component.ActivityComponent
import com.whoiszxl.order.injection.module.OrderModule
import com.whoiszxl.order.ui.activity.OrderConfirmActivity
import dagger.Component

/**
 * 订单Component
 */
@PerComponentScope
@Component(dependencies = [(ActivityComponent::class)],modules = [(OrderModule::class)])
interface OrderComponent {

    fun inject(activity: OrderConfirmActivity)

}
