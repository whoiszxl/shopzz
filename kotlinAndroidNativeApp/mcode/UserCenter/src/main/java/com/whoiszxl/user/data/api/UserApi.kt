package com.whoiszxl.user.data.api

import com.whoiszxl.base.data.protocol.BaseResp
import com.whoiszxl.user.data.protocol.RegisterReq
import retrofit2.http.Body
import retrofit2.http.POST
import rx.Observable

interface UserApi {

    @POST("/user/register")
    fun register(@Body req : RegisterReq):Observable<BaseResp<String>>
}
