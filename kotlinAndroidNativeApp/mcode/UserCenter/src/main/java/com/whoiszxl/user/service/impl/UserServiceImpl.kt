package com.whoiszxl.user.service.impl

import com.whoiszxl.base.data.protocol.BaseResp
import com.whoiszxl.user.data.repository.UserRepository
import com.whoiszxl.user.service.UserService
import rx.Observable
import rx.functions.Func1

class UserServiceImpl : UserService {
    override fun register(mobile: String, pwd: String, verifyCode: String): Observable<Boolean> {

        val repository = UserRepository()
        return repository.register(mobile, pwd, verifyCode)
                .flatMap(object :Func1<BaseResp<String>,
                        Observable<Boolean>> {
                    override fun call(t: BaseResp<String>):
                            Observable<Boolean> {
                        if(t.status != 0){
                            return Observable.error()
                        }
                        return Observable.just(true)
                    }
                })
    }
}