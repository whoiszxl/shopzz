package com.whoiszxl.order.injection.module

import com.whoiszxl.order.service.AddressService
import com.whoiszxl.order.service.impl.AddressServiceImpl
import dagger.Module
import dagger.Provides

/**
 * 收货人信息Module
 */
@Module
class ShipAddressModule {

    @Provides
    fun provideShipAddressservice(addressService: AddressServiceImpl): AddressService {
        return addressService
    }

}
