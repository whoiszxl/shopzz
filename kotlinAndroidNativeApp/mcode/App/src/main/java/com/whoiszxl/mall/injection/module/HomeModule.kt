package com.whoiszxl.mall.injection.module

import com.whoiszxl.mall.service.HomeService
import com.whoiszxl.mall.service.impl.HomeServiceImpl
import dagger.Module
import dagger.Provides

@Module
class HomeModule {

    @Provides
    fun provideHomeService(homeService: HomeServiceImpl): HomeService {
        return homeService
    }
}