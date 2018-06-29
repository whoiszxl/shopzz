package com.whoiszxl.user.injection.module

import com.whoiszxl.user.service.UploadService
import com.whoiszxl.user.service.impl.UploadServiceImpl
import dagger.Module
import dagger.Provides
/**
 * 上传module
 */
@Module
class UploadModule {

    @Provides
    fun provideUploadService(uploadService: UploadServiceImpl): UploadService {
        return uploadService
    }

}
