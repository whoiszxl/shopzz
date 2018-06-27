package com.whoiszxl.user.injection.module

import com.whoiszxl.user.service.UserService
import com.whoiszxl.user.service.impl.UserServiceImpl
import dagger.Module
import dagger.Provides

@Module
class UserModule {

    @Provides
    fun providesUserService(service: UserServiceImpl):UserService{
        return service
    }
}