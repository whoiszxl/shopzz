package com.whoiszxl.mall.service

import com.whoiszxl.mall.data.protocol.Banner
import rx.Observable

interface HomeService {

    fun getBanner():Observable<MutableList<Banner>?>
}