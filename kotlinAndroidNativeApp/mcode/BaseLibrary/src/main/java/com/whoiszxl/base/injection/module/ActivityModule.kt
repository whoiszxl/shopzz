package com.whoiszxl.base.injection.module

import android.app.Activity
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: Activity) {

    @Provides
    fun providesActivity():Activity{
        return activity
    }
}