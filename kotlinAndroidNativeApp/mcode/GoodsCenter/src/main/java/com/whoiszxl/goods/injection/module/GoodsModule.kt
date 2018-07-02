package com.whoiszxl.goods.injection.module

import com.whoiszxl.goods.service.GoodsService
import com.whoiszxl.goods.service.impl.GoodsServiceImpl
import dagger.Module
import dagger.Provides

/**
 * 商品Module
 */
@Module
class GoodsModule {

    @Provides
    fun provideGoodservice(goodsService: GoodsServiceImpl): GoodsService {
        return goodsService
    }

}
