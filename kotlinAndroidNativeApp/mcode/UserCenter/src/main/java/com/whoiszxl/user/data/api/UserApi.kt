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

    @FormUrlEncoded
    @POST("/user/app_register")
    fun register(
            @Field("phone") phone: String,
            @Field("password") password: String,
            @Field("verifyCode") verifyCode: String
    ): Observable<BaseResp<String>>

    @FormUrlEncoded
    @POST("/user/verifycode")
    fun verifycode(
            @Field("phone") phone: String
    ): Observable<BaseResp<String>>

    @FormUrlEncoded
    @POST("/user/app_login")
    fun login(
            @Field("username") username: String,
            @Field("password") password: String,
            @Field("push_id") push_id: String): Observable<BaseResp<UserInfo>>


    @FormUrlEncoded
    @POST("/user/forgetPwd")
    fun forgetPwd(
            @Field("phone") phone: String,
            @Field("verifyCode") verifyCode: String): Observable<BaseResp<String>>

    @FormUrlEncoded
    @POST("/user/resetPwd")
    fun resetPwd(
            @Field("phone") phone: String,
            @Field("password") password: String): Observable<BaseResp<String>>
}
