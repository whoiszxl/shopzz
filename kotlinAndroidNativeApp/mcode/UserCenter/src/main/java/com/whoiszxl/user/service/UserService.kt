package com.whoiszxl.user.service

import rx.Observable

interface UserService {
    fun register(mobile:String,verifyCode:String,pwd:String):Observable<Boolean>
}