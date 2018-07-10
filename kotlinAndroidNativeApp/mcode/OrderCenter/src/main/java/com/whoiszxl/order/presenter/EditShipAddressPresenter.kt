package com.whoiszxl.order.presenter

import com.whoiszxl.base.ext.execute
import com.whoiszxl.base.presenter.BasePresenter
import com.whoiszxl.base.rx.BaseSubscriber
import com.whoiszxl.base.utils.AppPrefsUtils
import com.whoiszxl.order.presenter.view.EditShipAddressView
import com.whoiszxl.order.service.AddressService
import com.whoiszxl.provider.common.ProviderConstant
import javax.inject.Inject

class EditShipAddressPresenter @Inject constructor() : BasePresenter<EditShipAddressView>() {

    @Inject
    lateinit var addressService: AddressService

    private var authToken = AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_SIGN)

    /**
     * 添加一条收货地址
     */
    fun addAddress(
            receiverName: String,
            receiverProvince: String,
            receiverCity: String,
            receiverAddress: String,
            receiverPhone: String,
            receiverZip: String) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        addressService.addressAdd(authToken, receiverName, receiverProvince, receiverCity, receiverAddress, receiverPhone, receiverZip)
                .execute(object : BaseSubscriber<Boolean>(mView) {
                    override fun onNext(t: Boolean) {
                        mView.onAddShipAddressResult(t)
                    }
                }, lifecycleProvider)
    }


    /**
     * 修改一条收货地址
     */
    fun editAddress(
            id: String,
            receiverName: String,
            receiverProvince: String,
            receiverCity: String,
            receiverAddress: String,
            receiverPhone: String,
            receiverZip: String) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        addressService.addressUpdate(authToken, receiverName, receiverProvince, receiverCity, receiverAddress, receiverPhone, receiverZip, id)
                .execute(object : BaseSubscriber<Boolean>(mView) {
                    override fun onNext(t: Boolean) {
                        mView.onEditShipAddressResult(t)
                    }
                }, lifecycleProvider)
    }

}
