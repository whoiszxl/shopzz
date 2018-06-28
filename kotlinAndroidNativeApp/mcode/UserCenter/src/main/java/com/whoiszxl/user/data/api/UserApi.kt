package com.whoiszxl.user.data.api

import com.whoiszxl.base.data.protocol.BaseResp
import com.whoiszxl.user.data.protocol.LoginReq
import com.whoiszxl.user.data.protocol.RegisterReq
import com.whoiszxl.user.data.protocol.UserInfo
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import rx.Observable

interface UserApi {

    @POST("/user/register")
    fun register(@Body req : RegisterReq):Observable<BaseResp<String>>

    @FormUrlEncoded
    @POST("/user/app_login")
    fun login(
            @Field("username") username:String,
            @Field("password") password:String,
            @Field("push_id") push_id:String):Observable<BaseResp<UserInfo>>


    @FormUrlEncoded
    @POST("/user/app_login")
    fun forgetPwd(
            @Field("username") username:String,
            @Field("password") password:String,
            @Field("push_id") push_id:String):Observable<BaseResp<UserInfo>>
}
