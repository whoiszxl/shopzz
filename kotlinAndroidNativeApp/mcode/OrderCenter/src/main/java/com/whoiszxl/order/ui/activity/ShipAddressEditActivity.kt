package com.whoiszxl.order.ui.activity

import android.os.Bundle
import com.whoiszxl.base.ext.onClick
import com.whoiszxl.base.ui.activity.BaseMvpActivity
import com.whoiszxl.order.R
import com.whoiszxl.order.common.OrderConstant
import com.whoiszxl.order.data.protocol.ShipAddress
import com.whoiszxl.order.injection.component.DaggerShipAddressComponent
import com.whoiszxl.order.injection.module.ShipAddressModule
import com.whoiszxl.order.presenter.EditShipAddressPresenter
import com.whoiszxl.order.presenter.view.EditShipAddressView
import kotlinx.android.synthetic.main.activity_edit_address.*
import org.jetbrains.anko.toast

/*
    收货人编辑页面
 */
class ShipAddressEditActivity : BaseMvpActivity<EditShipAddressPresenter>(), EditShipAddressView {

    private var mAddress: ShipAddress? = null

    override fun injectComponent() {
        DaggerShipAddressComponent.builder().activityComponent(mActivityComponent).shipAddressModule(ShipAddressModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_address)

        initView()
        initData()
    }

    /*
        初始化视图
     */
    private fun initView() {

        mSaveBtn.onClick {
            if (mShipNameEt.text.isNullOrEmpty()){
                toast("名称不能为空")
                return@onClick
            }
            if (mShipMobileEt.text.isNullOrEmpty()){
                toast("电话不能为空")
                return@onClick
            }
            if (mShipAddressEt.text.isNullOrEmpty()){
                toast("地址不能为空")
                return@onClick
            }
            if (mAddress == null) {
                mPresenter.addAddress(
                        mShipNameEt.text.toString(),
                        mShipProvinceEt.text.toString(),
                        mShipCityEt.text.toString(),
                        mShipAddressEt.text.toString(),
                        mShipMobileEt.text.toString(),
                        mShipZipEt.text.toString()
                )
            }else{
                mPresenter.editAddress(
                        mAddress!!.id.toString(),
                        mShipNameEt.text.toString(),
                        mShipProvinceEt.text.toString(),
                        mShipCityEt.text.toString(),
                        mShipAddressEt.text.toString(),
                        mShipMobileEt.text.toString(),
                        mShipZipEt.text.toString()
                )
            }
        }
    }

    /*
        初始化数据
     */
    private fun initData() {
        mAddress = intent.getParcelableExtra(OrderConstant.KEY_SHIP_ADDRESS)
        mAddress?.let {
            mShipNameEt.setText(it.receiverName)
            mShipMobileEt.setText(it.receiverPhone)
            mShipProvinceEt.setText(it.receiverProvince)
            mShipCityEt.setText(it.receiverCity)
            mShipAddressEt.setText(it.receiverAddress)
            mShipZipEt.setText(it.receiverZip)
        }

    }

    /**
     * 添加收货人信息回调
     */
    override fun onAddShipAddressResult(result: Boolean) {
        toast("添加地址成功")
        finish()
    }

    /**
     * 修改收货人信息回调
     */
    override fun onEditShipAddressResult(result: Boolean) {
        toast("修改地址成功")
        finish()
    }
}