package com.whoiszxl.order.injection.module

import com.whoiszxl.order.service.OrderService
import com.whoiszxl.order.service.impl.OrderServiceImpl
import dagger.Module
import dagger.Provides

/**
 * 订单Module
 */
@Module
class OrderModule {

    @Provides
    fun provideOrderservice(orderService: OrderServiceImpl): OrderService {
        return orderService
    }

}
