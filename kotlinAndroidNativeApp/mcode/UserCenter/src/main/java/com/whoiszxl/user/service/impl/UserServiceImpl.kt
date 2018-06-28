package com.whoiszxl.user.service.impl

import com.whoiszxl.base.data.protocol.BaseResp
import com.whoiszxl.base.ext.convert
import com.whoiszxl.base.ext.convertBoolean
import com.whoiszxl.base.rx.BaseException
import com.whoiszxl.base.rx.BaseFuncBoolean
import com.whoiszxl.user.data.protocol.UserInfo
import com.whoiszxl.user.data.repository.UserRepository
import com.whoiszxl.user.service.UserService
import rx.Observable
import rx.functions.Func1
import javax.inject.Inject

class UserServiceImpl @Inject constructor() : UserService {


    @Inject
    lateinit var repository: UserRepository

    /**
     * 注册服务接口
     */
    override fun register(mobile: String, pwd: String, verifyCode: String): Observable<Boolean> {

        return repository.register(mobile, pwd, verifyCode).convertBoolean()
    }

    /**
     * 登录服务接口
     */
    override fun login(mobile: String, pwd: String, pushId: String): Observable<UserInfo> {
        return repository.login(mobile, pwd, pushId).convert()

    }
}