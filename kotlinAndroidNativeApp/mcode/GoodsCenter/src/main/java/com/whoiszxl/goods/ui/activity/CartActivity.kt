package com.whoiszxl.goods.ui.activity

import android.os.Bundle
import com.whoiszxl.base.ui.activity.BaseActivity
import com.whoiszxl.goods.R
import com.whoiszxl.goods.ui.fragment.CartFragment

/*
    购物车Activity
    只是Fragment一个壳
 */
class CartActivity:BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        val fragment = supportFragmentManager.findFragmentById(R.id.fragment_cart)
        (fragment as CartFragment).setBackVisible(true)

    }
}
