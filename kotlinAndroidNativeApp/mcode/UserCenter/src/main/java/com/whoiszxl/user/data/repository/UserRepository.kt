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

    /**
     * 发送注册验证码
     */
    fun verifycode(phone:String):Observable<BaseResp<String>>{
        return RetrofitFactory.instance.create(UserApi::class.java)
                .verifycode(phone)
    }

    /**
     * 发送注册验证码
     */
    fun forgetpwd_verifycode(phone:String):Observable<BaseResp<String>>{
        return RetrofitFactory.instance.create(UserApi::class.java)
                .forgetpwd_verifycode(phone)
    }

    /**
     * 注册
     */
    fun register(phone:String,pwd:String,verifyCode:String):Observable<BaseResp<String>>{
        return RetrofitFactory.instance.create(UserApi::class.java)
                .register(phone, pwd, verifyCode)
    }

    /**
     * 登录
     */
    fun login(phone:String, pwd:String, pushId:String):Observable<BaseResp<UserInfo>>{
        return RetrofitFactory.instance.create(UserApi::class.java)
                .login(phone, pwd, pushId)
    }

    /**
     * 忘记密码
     */
    fun forgetPwd(phone:String, verifyCode:String):Observable<BaseResp<String>>{
        return RetrofitFactory.instance.create(UserApi::class.java)
                .forgetPwd(phone, verifyCode)
    }

    /**
     * 重置密码
     */
    fun resetPwd(phone:String, pwd:String, verifyCode: String):Observable<BaseResp<String>>{
        return RetrofitFactory.instance.create(UserApi::class.java)
                .resetPwd(phone, pwd, verifyCode)
    }

    /**
     * 编辑用户资料
     */
    fun editUser(userIcon:String,userName:String,userGender:String,userSign:String):Observable<BaseResp<UserInfo>>{
        return RetrofitFactory.instance.create(UserApi::class.java).editUser(userIcon,userName,userGender,userSign)
    }
}