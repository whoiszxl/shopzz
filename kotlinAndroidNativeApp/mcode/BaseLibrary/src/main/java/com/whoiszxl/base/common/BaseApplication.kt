package com.whoiszxl.base.common

import android.app.Application
import android.content.Context
import com.whoiszxl.base.injection.component.AppComponent
import com.whoiszxl.base.injection.component.DaggerAppComponent
import com.whoiszxl.base.injection.module.AppModule

class BaseApplication : Application(){

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        initAppInjection()

        context = this
    }

    /**
     * Application Component初始化
     */
    private fun initAppInjection() {
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()

    }

    /*
        全局伴生对象
     */
    companion object {
        lateinit var context: Context
    }
}