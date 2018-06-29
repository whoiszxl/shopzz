package com.whoiszxl.user.service

import com.whoiszxl.user.data.protocol.UserInfo
import rx.Observable

interface UserService {

    fun verifycode(mobile: String):Observable<Boolean>

    fun forgetpwdVerifycode(mobile: String):Observable<Boolean>

    fun register(mobile:String,pwd:String,verifyCode:String):Observable<Boolean>

    fun login(mobile:String,pwd:String,pushId:String):Observable<UserInfo>

    fun forgetPwd(mobile:String, verifyCode:String):Observable<Boolean>

    fun resetPwd(mobile:String,pwd:String, verifyCode: String):Observable<Boolean>

    fun editUser(userIcon:String,userName:String,userGender:String,userSign:String):Observable<UserInfo>
}