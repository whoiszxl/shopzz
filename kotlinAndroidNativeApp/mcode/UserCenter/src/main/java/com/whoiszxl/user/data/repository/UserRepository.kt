package com.whoiszxl.user.data.repository

import com.whoiszxl.base.data.net.RetrofitFactory
import com.whoiszxl.base.data.protocol.BaseResp
import com.whoiszxl.user.data.api.UserApi
import com.whoiszxl.user.data.protocol.RegisterReq
import rx.Observable

class UserRepository {

    fun register(mobile:String,pwd:String,verifyCode:String):Observable<BaseResp<String>>{
        return RetrofitFactory.instance.create(UserApi::class.java)
                .register(RegisterReq(mobile, pwd, verifyCode))
    }
}