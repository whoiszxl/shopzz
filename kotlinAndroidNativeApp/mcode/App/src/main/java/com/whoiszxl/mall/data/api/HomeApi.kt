package com.whoiszxl.mall.data.api

import com.whoiszxl.base.data.protocol.BaseResp
import com.whoiszxl.mall.data.protocol.Banner
import retrofit2.http.GET
import rx.Observable

interface HomeApi {

    @GET("/article/banners")
    fun getBanner(): Observable<BaseResp<MutableList<Banner>?>>
}