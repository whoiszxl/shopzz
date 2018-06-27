package com.whoiszxl.user.service.impl

import com.whoiszxl.base.data.protocol.BaseResp
import com.whoiszxl.base.ext.convertBoolean
import com.whoiszxl.base.rx.BaseException
import com.whoiszxl.base.rx.BaseFuncBoolean
import com.whoiszxl.user.data.repository.UserRepository
import com.whoiszxl.user.service.UserService
import rx.Observable
import rx.functions.Func1
import javax.inject.Inject

class UserServiceImpl @Inject constructor() : UserService {

    @Inject
    lateinit var repository: UserRepository

    override fun register(mobile: String, pwd: String, verifyCode: String): Observable<Boolean> {

        return repository.register(mobile, pwd, verifyCode)
                .convertBoolean()
    }
}