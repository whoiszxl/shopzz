package com.whoiszxl.user.data.repository

import com.whoiszxl.base.data.net.RetrofitFactory
import com.whoiszxl.base.data.protocol.BaseResp
import com.whoiszxl.user.data.api.UserApi
import com.whoiszxl.user.data.protocol.LoginReq
import com.whoiszxl.user.data.protocol.RegisterReq
import com.whoiszxl.user.data.protocol.UserInfo
import rx.Observable
import javax.inject.Inject

class UserRepository @Inject constructor(){

    fun register(mobile:String,pwd:String,verifyCode:String):Observable<BaseResp<String>>{
        return RetrofitFactory.instance.create(UserApi::class.java)
                .register(RegisterReq(mobile, pwd, verifyCode))
    }

    fun login(mobile:String, pwd:String, pushId:String):Observable<BaseResp<UserInfo>>{
        return RetrofitFactory.instance.create(UserApi::class.java)
                .login(mobile, pwd, pushId)
    }
}