package com.whoiszxl.base.injection.component

import android.app.Activity
import android.content.Context
import com.trello.rxlifecycle.LifecycleProvider
import com.whoiszxl.base.injection.ActivityScope
import com.whoiszxl.base.injection.module.ActivityModule
import com.whoiszxl.base.injection.module.LifecycleProviderModule
import dagger.Component

@ActivityScope
@Component(
        dependencies = arrayOf(AppComponent::class),
        modules = arrayOf(ActivityModule::class,LifecycleProviderModule::class))
interface ActivityComponent {
    fun activity():Activity
    fun context():Context
    fun lifecycleProvider():LifecycleProvider<*>
}