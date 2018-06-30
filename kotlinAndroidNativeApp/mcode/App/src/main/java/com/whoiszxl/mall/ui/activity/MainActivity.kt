package com.whoiszxl.mall.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.whoiszxl.mall.R
import com.whoiszxl.mall.ui.fragment.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mBottomNavBar.checkMsgBadge(false)
        mBottomNavBar.checkCartBadge(20)

        initView()
    }

    private fun initView() {
        val manager = supportFragmentManager.beginTransaction()
        manager.replace(R.id.mContainer, HomeFragment())
        manager.commit()
    }
}
