package com.whoiszxl.mall.data.repository

import com.whoiszxl.base.data.net.RetrofitFactory
import com.whoiszxl.base.data.protocol.BaseResp
import com.whoiszxl.mall.data.api.HomeApi
import com.whoiszxl.mall.data.protocol.Banner
import rx.Observable
import javax.inject.Inject

class HomeRepository @Inject constructor() {

    /**
     * 获取轮播图
     */
    fun getBanner():Observable<BaseResp<MutableList<Banner>?>> {
        return RetrofitFactory.instance.create(HomeApi::class.java)
                .getBanner()
    }

}