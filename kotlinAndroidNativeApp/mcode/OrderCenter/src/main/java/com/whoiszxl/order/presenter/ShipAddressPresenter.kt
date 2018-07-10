package com.whoiszxl.order.presenter

import com.whoiszxl.base.common.BaseConstant
import com.whoiszxl.base.ext.execute
import com.whoiszxl.base.presenter.BasePresenter
import com.whoiszxl.base.rx.BaseSubscriber
import com.whoiszxl.base.utils.AppPrefsUtils
import com.whoiszxl.order.data.protocol.AddressList
import com.whoiszxl.order.presenter.view.ShipAddressView
import com.whoiszxl.order.service.AddressService
import com.whoiszxl.provider.common.ProviderConstant
import javax.inject.Inject

class ShipAddressPresenter @Inject constructor(): BasePresenter<ShipAddressView>(){

    @Inject
    lateinit var addressService: AddressService

    private var authToken = AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_SIGN)

    /**
     * 获取收货地址列表
     */
    fun getShipAddressList() {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()

        addressService.addressList(authToken,BaseConstant.ADDRESS_SHOW_COUNT).execute(object : BaseSubscriber<AddressList>(mView) {
            override fun onNext(t: AddressList) {
                mView.onGetShipAddressResult(t)
            }
        }, lifecycleProvider)
    }

    /**
     * 删除收货人信息
     */
    fun deleteShipAddress(shippingId:Int) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        addressService.addressDelete(authToken, shippingId).execute(object : BaseSubscriber<Boolean>(mView) {
            override fun onNext(t: Boolean) {
                mView.onDeleteResult(t)
            }
        }, lifecycleProvider)

    }

    fun setAddressIsDefault(shippingId: Int) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        addressService.addressSetDefault(authToken, shippingId).execute(object : BaseSubscriber<Boolean>(mView) {
            override fun onNext(t: Boolean) {
                mView.onSetDefaultResult(t)
            }
        }, lifecycleProvider)
    }
}