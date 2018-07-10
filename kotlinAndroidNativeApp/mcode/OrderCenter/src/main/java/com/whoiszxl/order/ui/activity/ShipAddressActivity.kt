package com.whoiszxl.order.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.bigkoo.alertview.AlertView
import com.bigkoo.alertview.OnItemClickListener
import com.eightbitlab.rxbus.Bus
import com.kennyc.view.MultiStateView
import com.whoiszxl.base.ext.onClick
import com.whoiszxl.base.ext.startLoading
import com.whoiszxl.base.ui.activity.BaseMvpActivity
import com.whoiszxl.base.ui.adapter.BaseRecyclerViewAdapter
import com.whoiszxl.order.R
import com.whoiszxl.order.common.OrderConstant
import com.whoiszxl.order.data.protocol.AddressList
import com.whoiszxl.order.data.protocol.ShipAddress
import com.whoiszxl.order.event.SelectAddressEvent
import com.whoiszxl.order.injection.component.DaggerShipAddressComponent
import com.whoiszxl.order.injection.module.ShipAddressModule
import com.whoiszxl.order.presenter.ShipAddressPresenter
import com.whoiszxl.order.presenter.view.ShipAddressView
import com.whoiszxl.order.ui.adapter.ShipAddressAdapter
import kotlinx.android.synthetic.main.activity_address.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast


/*
    收货人信息列表页
 */
class ShipAddressActivity: BaseMvpActivity<ShipAddressPresenter>(), ShipAddressView {

    private lateinit var mAdapter : ShipAddressAdapter


    /**
     * Dagger注册
     */
    override fun injectComponent() {
        DaggerShipAddressComponent.builder().activityComponent(mActivityComponent).shipAddressModule(ShipAddressModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address)
        initView()
    }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    /**
     * 加载数据
     */
    private fun loadData() {
        mMultiStateView.startLoading()
        mPresenter.getShipAddressList()
    }

    /**
     * 初始化视图
     */
    private fun initView() {
        mAddressRv.layoutManager = LinearLayoutManager(this)
        mAdapter = ShipAddressAdapter(this)
        mAddressRv.adapter = mAdapter

        //设置操作事件
        mAdapter.mOptClickListener = object : ShipAddressAdapter.OnOptClickListener {
            override fun onSetDefault(address: ShipAddress) {
                //todo 设置默认地址事件
                toast("设置默认地址")
            }

            override fun onEdit(address: ShipAddress) {
                startActivity<ShipAddressEditActivity>(OrderConstant.KEY_SHIP_ADDRESS to address)
            }

            override fun onDelete(address: ShipAddress) {
                AlertView("删除", "确定删除该地址吗？", "取消", null, arrayOf("确定"), this@ShipAddressActivity,
                        AlertView.Style.Alert, OnItemClickListener{o, position ->
                    if(position == 0) {
                        mPresenter.deleteShipAddress(address.id)
                    }
                }).show()
            }
        }

        //单项点击事件
        mAdapter.setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<ShipAddress>{
            override fun onItemClick(item: ShipAddress, position: Int) {
                Bus.send(SelectAddressEvent(item))
                finish()
            }
        })


        mAddAddressBtn.onClick {
            startActivity<ShipAddressEditActivity>()
        }
    }

    override fun onGetShipAddressResult(result: AddressList) {
        if(result!=null && result.list!!.size > 0) {
            mAdapter.setData(result.list)
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_CONTENT
        } else {
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_EMPTY
        }
    }

    override fun onSetDefaultResult(result: Boolean) {
        toast("设置默认成功")
        loadData()
    }

    override fun onDeleteResult(result: Boolean) {
        toast("删除成功")
        loadData()
    }
}