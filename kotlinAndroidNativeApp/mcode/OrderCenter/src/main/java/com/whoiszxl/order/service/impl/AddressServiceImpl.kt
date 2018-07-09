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

    override fun addressAdd(receiverName: String, receiverProvince: String, receiverCity: String, receiverAddress: String, receiverPhone: String, receiverZip: String): Observable<Boolean> {
        return repository.addressAdd(receiverName, receiverProvince, receiverCity, receiverAddress, receiverPhone, receiverZip).convertBoolean()
    }

    override fun addressDelete(shippingId: Int): Observable<Boolean> {
        return repository.addressDelete(shippingId).convertBoolean()
    }

    override fun addressUpdate(receiverName: String, receiverProvince: String, receiverCity: String, receiverAddress: String, receiverPhone: String, receiverZip: String, id: String): Observable<Boolean> {
        return repository.addressUpdate(receiverName, receiverProvince, receiverCity, receiverAddress, receiverPhone, receiverZip, id).convertBoolean()
    }

    override fun addressList(pageSize: Int): Observable<AddressList> {
        return repository.addressList(pageSize).convert()
    }

    override fun addressSelects(shippingId: Int): Observable<ShipAddress> {
        return repository.addressSelects(shippingId).convert()
    }

}