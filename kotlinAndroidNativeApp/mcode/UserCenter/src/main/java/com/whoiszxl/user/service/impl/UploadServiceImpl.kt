package com.whoiszxl.user.service.impl

import com.whoiszxl.base.ext.convert
import com.whoiszxl.user.data.repository.UploadRepository
import com.whoiszxl.user.service.UploadService
import rx.Observable
import javax.inject.Inject

/*
    上传业务实现类
 */
class UploadServiceImpl @Inject constructor(): UploadService {

    @Inject
    lateinit var repository: UploadRepository

    override fun getUploadToken(): Observable<String> {
        return repository.getUploadToken().convert()
    }

}