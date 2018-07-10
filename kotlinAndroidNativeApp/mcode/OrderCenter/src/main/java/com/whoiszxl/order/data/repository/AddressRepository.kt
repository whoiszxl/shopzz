package com.whoiszxl.order.data.repository

import com.whoiszxl.base.data.net.RetrofitFactory
import com.whoiszxl.base.data.protocol.BaseResp
import com.whoiszxl.order.data.api.ShipAddressApi
import com.whoiszxl.order.data.protocol.AddressList
import com.whoiszxl.order.data.protocol.ShipAddress
import rx.Observable
import javax.inject.Inject

class AddressRepository @Inject constructor() {
    /**
     * 添加收货地址
     */
    fun addressAdd(
            authorization: String,
            receiverName: String,
            receiverProvince: String,
            receiverCity: String,
            receiverAddress: String,
            receiverPhone: String,
            receiverZip: String): Observable<BaseResp<String>> {
        return RetrofitFactory.instance.create(ShipAddressApi::class.java).addressAdd(authorization, receiverName, receiverProvince, receiverCity, receiverAddress, receiverPhone, receiverZip)
    }

    /**
     * 删除收货地址
     */
    fun addressDelete(
            authorization: String,
            shippingId: Int
    ): Observable<BaseResp<String>> {
        return RetrofitFactory.instance.create(ShipAddressApi::class.java).addressDelete(authorization, shippingId)
    }

    /**
     * 修改收货地址
     */
    fun addressUpdate(
            authorization: String,
            receiverName: String,
            receiverProvince: String,
            receiverCity: String,
            receiverAddress: String,
            receiverPhone: String,
            receiverZip: String,
            id: String): Observable<BaseResp<String>> {
        return RetrofitFactory.instance.create(ShipAddressApi::class.java).addressUpdate(authorization, receiverName, receiverProvince, receiverCity, receiverAddress, receiverPhone, receiverZip, id)
    }

    /**
     * 查询收货地址列表
     */
    fun addressList(
            authorization: String,
            pageSize: Int
    ): Observable<BaseResp<AddressList>> {
        return RetrofitFactory.instance.create(ShipAddressApi::class.java).addressList(authorization, pageSize)
    }


    /**
     * 通过id查询地址
     */
    fun addressSelects(
            authorization: String,
            shippingId: Int
    ): Observable<BaseResp<ShipAddress>> {
        return RetrofitFactory.instance.create(ShipAddressApi::class.java).addressSelects(authorization, shippingId)
    }

    fun addressSetDefault()
}