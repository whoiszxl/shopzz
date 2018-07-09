package com.whoiszxl.order.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.whoiszxl.base.ext.onClick
import com.whoiszxl.base.ui.activity.BaseMvpActivity
import com.whoiszxl.order.R
import com.whoiszxl.order.data.protocol.Order
import com.whoiszxl.order.injection.component.DaggerOrderComponent
import com.whoiszxl.order.injection.module.OrderModule
import com.whoiszxl.order.presenter.OrderConfirmPresenter
import com.whoiszxl.order.presenter.view.OrderConfirmView
import com.whoiszxl.order.ui.adapter.OrderGoodsAdapter
import com.whoiszxl.provider.router.RouterPath
import kotlinx.android.synthetic.main.activity_order_confirm.*
import org.jetbrains.anko.startActivity

@Route(path = RouterPath.OrderCenter.PATH_ORDER_CONFIRM)
class OrderConfirmActivity : BaseMvpActivity<OrderConfirmPresenter>(),OrderConfirmView {


    private lateinit var mAdapter:OrderGoodsAdapter

    /**
     * Dagger注册
     */
    override fun injectComponent() {
        DaggerOrderComponent.builder().activityComponent(mActivityComponent).orderModule(OrderModule()).build().inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_confirm)

        initView()
    }

    /**
     * 初始化视图
     */
    private fun initView() {
        mShipView.onClick {
            //startActivity<ShipAddressActivity>()
        }
        mSelectShipTv.onClick {
            //startActivity<ShipAddressActivity>()

        }

        mSubmitOrderBtn.onClick {
//            mCurrentOrder?.let {
//                mPresenter.submitOrder(it)
//            }
        }

        //订单中商品列表
        mOrderGoodsRv.layoutManager = LinearLayoutManager(this)
        mAdapter = OrderGoodsAdapter(this)
        mOrderGoodsRv.adapter = mAdapter
    }

    override fun onGetOrderByIdResult(result: Order) {

    }

    override fun onSubmitOrderResult(result: Boolean) {

    }
}