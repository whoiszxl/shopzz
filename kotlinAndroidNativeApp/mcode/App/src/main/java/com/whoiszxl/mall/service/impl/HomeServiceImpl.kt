package com.whoiszxl.mall.service.impl

import com.whoiszxl.base.ext.convert
import com.whoiszxl.mall.data.protocol.Banner
import com.whoiszxl.mall.data.repository.HomeRepository
import com.whoiszxl.mall.service.HomeService
import rx.Observable
import javax.inject.Inject

class HomeServiceImpl @Inject constructor():HomeService {

    @Inject
    lateinit var repository: HomeRepository

    /**
     * 获取轮播图服务接口
     */
    override fun getBanner(): Observable<MutableList<Banner>?> {
        return repository.getBanner().convert()
    }
}