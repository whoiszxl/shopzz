package com.whoiszxl.order.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.whoiszxl.base.ui.activity.BaseMvpActivity
import com.whoiszxl.base.utils.YuanFenConverter
import com.whoiszxl.order.R
import com.whoiszxl.order.data.protocol.Order
import com.whoiszxl.order.injection.component.DaggerOrderComponent
import com.whoiszxl.order.injection.module.OrderModule
import com.whoiszxl.order.presenter.OrderDetailPresenter
import com.whoiszxl.order.presenter.view.OrderDetailView
import com.whoiszxl.order.ui.adapter.OrderGoodsAdapter
import com.whoiszxl.provider.common.ProviderConstant
import com.whoiszxl.provider.router.RouterPath
import kotlinx.android.synthetic.main.activity_order_detail.*

/*
    订单详情
 */
@Route(path = RouterPath.MessageCenter.PATH_MESSAGE_ORDER)
class OrderDetailActivity : BaseMvpActivity<OrderDetailPresenter>(), OrderDetailView {
    private lateinit var mAdapter: OrderGoodsAdapter

    /*
        Dagger注册
     */
    override fun injectComponent() {
        DaggerOrderComponent.builder().activityComponent(mActivityComponent).orderModule(OrderModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_detail)

        initView()
        loadData()
    }

    /*
        初始化视图
     */
    private fun initView() {
        mOrderGoodsRv.layoutManager = LinearLayoutManager(this)
        mAdapter = OrderGoodsAdapter(this)
        mOrderGoodsRv.adapter = mAdapter
    }

    /*
        加载数据
     */
    private fun loadData() {
        mPresenter.getOrderByOrderNo(intent.getStringExtra(ProviderConstant.KEY_ORDER_ID))
    }

    /*
        获取订单回调
     */
    override fun onGetOrderByIdResult(result: Order) {
        mShipNameTv.setContentText(result.shippingVo!!.receiverName)
        mShipMobileTv.setContentText(result.shippingVo!!.receiverPhone)
        mShipAddressTv.setContentText(result.shippingVo!!.receiverProvince+result.shippingVo!!.receiverCity+result.shippingVo!!.receiverAddress)
        mTotalPriceTv.setContentText(YuanFenConverter.changeF2YWithUnit(result.productTotalPrice))

        mAdapter.setData(result.orderItemVoList)
    }

}
