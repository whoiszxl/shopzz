package com.whoiszxl.user.injection.component

import com.whoiszxl.base.injection.PerComponentScope
import com.whoiszxl.base.injection.component.ActivityComponent
import com.whoiszxl.user.injection.module.UserModule
import com.whoiszxl.user.ui.activity.RegisterActivity
import dagger.Component

@PerComponentScope
@Component(
        dependencies = arrayOf(ActivityComponent::class),
        modules = arrayOf(UserModule::class))
interface UserComponent {

    fun inject(activity: RegisterActivity)
}