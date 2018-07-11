package com.whoiszxl.order.ui.activity

import android.os.Bundle
import android.support.design.widget.TabLayout
import com.whoiszxl.base.ui.activity.BaseActivity
import com.whoiszxl.order.R
import com.whoiszxl.order.common.OrderConstant
import com.whoiszxl.order.common.OrderStatus
import com.whoiszxl.order.ui.adapter.OrderVpAdapter
import kotlinx.android.synthetic.main.activity_order.*

/**
 * 订单Activity
 * 主要包括不同订单状态的Fragment
 */
class OrderActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        initView()
    }

    private fun initView() {
        mOrderTab.tabMode = TabLayout.MODE_FIXED
        mOrderVp.adapter = OrderVpAdapter(supportFragmentManager,this)
        mOrderTab.setupWithViewPager(mOrderVp)

        //根据订单状态设置当前页面
        mOrderVp.currentItem = intent.getIntExtra(OrderConstant.KEY_ORDER_STATUS, OrderStatus.ORDER_ALL)
    }
}