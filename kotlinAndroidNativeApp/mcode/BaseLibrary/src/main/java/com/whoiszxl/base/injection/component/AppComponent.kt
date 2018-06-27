package com.whoiszxl.base.injection.component

import android.content.Context
import com.whoiszxl.base.injection.module.AppModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {
    fun context():Context
}