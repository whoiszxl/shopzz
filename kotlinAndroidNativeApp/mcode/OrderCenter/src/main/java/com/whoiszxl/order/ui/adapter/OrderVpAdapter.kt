package com.whoiszxl.order.ui.adapter


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.whoiszxl.order.common.OrderConstant
import com.whoiszxl.order.common.OrderStatus
import com.whoiszxl.order.ui.fragment.OrderFragment

/*
    订单Tab对应ViewPager
 */
class OrderVpAdapter(fm: FragmentManager, context: Context)
    : FragmentPagerAdapter(fm) {

    private val titles = arrayOf("全部","待付款","待收货","已完成","已取消")


    override fun getItem(position: Int): Fragment {
        val fragment = OrderFragment()
        val bundle = Bundle()
        bundle.putInt(OrderConstant.KEY_ORDER_STATUS,position)
        fragment.arguments = bundle
        return fragment

    }

    override fun getCount(): Int {
        return titles.size
    }

    override fun getPageTitle(position: Int): CharSequence {
//        return when(position) {
//            OrderStatus.ORDER_ALL -> titles[0]
//            OrderStatus.ORDER_WAIT_PAY -> titles[1]
//            OrderStatus.ORDER_WAIT_CONFIRM -> titles[2]
//            OrderStatus.ORDER_COMPLETED -> titles[3]
//            OrderStatus.ORDER_CANCELED -> titles[4]
//            else -> titles[0]
//        }
        return titles[position]

    }

}
