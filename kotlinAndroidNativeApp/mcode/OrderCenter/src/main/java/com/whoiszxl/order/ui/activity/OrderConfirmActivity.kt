package com.whoiszxl.order.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.whoiszxl.base.ext.onClick
import com.whoiszxl.base.ui.activity.BaseMvpActivity
import com.whoiszxl.base.utils.YuanFenConverter
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
import org.jetbrains.anko.toast

@Route(path = RouterPath.OrderCenter.PATH_ORDER_CONFIRM)
class OrderConfirmActivity : BaseMvpActivity<OrderConfirmPresenter>(),OrderConfirmView {


    private lateinit var mAdapter:OrderGoodsAdapter

    private var mCurrentOrder: Order? = null

    /**
     * Dagger注册
     */
    override fun injectComponent() {
        DaggerOrderComponent.builder().activityComponent(mActivityComponent).orderModule(OrderModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_confirm)

        initView()
        initObserve()
        loadData()
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

    /**
     *  初始化选择收货人事件监听
     */
    private fun initObserve() {

    }

    /**
     * 加载数据
     */
    private fun loadData() {
        mPresenter.getOrderCheckProduct()
    }

    @SuppressLint("SetTextI18n")
    override fun onGetOrderCheckProductResult(result: Order) {
        mCurrentOrder = result
        mAdapter.setData(result.orderItemVoList)
        mAdapter.setImageHost(result.imageHost)
        mTotalPriceTv.text = "合计：${YuanFenConverter.changeF2YWithUnit(result.productTotalPrice)}"
    }

    override fun onSubmitOrderResult(result: Boolean) {
        toast("提交订单成功喔")

    }
}