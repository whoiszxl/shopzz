package com.whoiszxl.goods.injection.module

import com.whoiszxl.goods.service.CartService
import com.whoiszxl.goods.service.impl.CartServiceImpl
import dagger.Module
import dagger.Provides

/**
 * 购物车Module
 */
@Module
class CartModule {

    @Provides
    fun provideCartservice(cartService: CartServiceImpl): CartService {
        return cartService
    }

}