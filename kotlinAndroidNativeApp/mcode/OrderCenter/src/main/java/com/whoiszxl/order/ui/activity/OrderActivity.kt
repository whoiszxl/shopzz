package com.whoiszxl.order.ui.activity

import android.os.Bundle
import android.support.design.widget.TabLayout
import com.whoiszxl.base.ui.activity.BaseActivity
import com.whoiszxl.order.R
import com.whoiszxl.order.common.OrderConstant
import com.whoiszxl.order.common.OrderStatus
import com.whoiszxl.order.ui.adapter.OrderVpAdapter
import com.whoiszxl.order.utils.OrderStatusConverter
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

    /**
     * 初始化视图
     */
    private fun initView() {
        //通过OrderVpAdapter设置TabLayout ViewPager滑动显示的栏和内容
        mOrderTab.tabMode = TabLayout.MODE_FIXED
        mOrderVp.adapter = OrderVpAdapter(supportFragmentManager,this)
        mOrderTab.setupWithViewPager(mOrderVp)




        //因為接口返回的訂單狀態和viewPager的下標對不上，所以要進行一下轉換
        var beforePosition:Int = intent.getIntExtra(OrderConstant.KEY_ORDER_STATUS, OrderStatus.ORDER_ALL)
        var afterPosition = OrderStatusConverter.Api2App(beforePosition)

        //根据订单状态设置当前页面
        mOrderVp.currentItem = afterPosition
    }
}