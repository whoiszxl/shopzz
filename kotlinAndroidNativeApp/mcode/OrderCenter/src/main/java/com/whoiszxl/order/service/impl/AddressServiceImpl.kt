package com.whoiszxl.order.service.impl

import com.whoiszxl.base.ext.convert
import com.whoiszxl.base.ext.convertBoolean
import com.whoiszxl.order.data.protocol.AddressList
import com.whoiszxl.order.data.protocol.ShipAddress
import com.whoiszxl.order.data.repository.AddressRepository
import com.whoiszxl.order.service.AddressService
import rx.Observable
import javax.inject.Inject

/**
 * 地址服务实现接口
 */
class AddressServiceImpl @Inject constructor(): AddressService {


    @Inject
    lateinit var repository:AddressRepository

    override fun addressAdd(authorization: String, receiverName: String, receiverProvince: String, receiverCity: String, receiverAddress: String, receiverPhone: String, receiverZip: String): Observable<Boolean> {
        return repository.addressAdd(authorization, receiverName, receiverProvince, receiverCity, receiverAddress, receiverPhone, receiverZip).convertBoolean()
    }

    override fun addressDelete(authorization: String, shippingId: Int): Observable<Boolean> {
        return repository.addressDelete(authorization, shippingId).convertBoolean()
    }

    override fun addressUpdate(authorization: String, receiverName: String, receiverProvince: String, receiverCity: String, receiverAddress: String, receiverPhone: String, receiverZip: String, id: String): Observable<Boolean> {
        return repository.addressUpdate(authorization, receiverName, receiverProvince, receiverCity, receiverAddress, receiverPhone, receiverZip, id).convertBoolean()
    }

    override fun addressList(authorization: String, pageSize: Int): Observable<AddressList> {
        return repository.addressList(authorization, pageSize).convert()
    }

    override fun addressSelects(authorization: String, shippingId: Int): Observable<ShipAddress> {
        return repository.addressSelects(authorization, shippingId).convert()
    }

    override fun addressSetDefault(authorization: String, shippingId: Int): Observable<Boolean> {
        return repository.addressSetDefault(authorization, shippingId).convertBoolean()
    }

}