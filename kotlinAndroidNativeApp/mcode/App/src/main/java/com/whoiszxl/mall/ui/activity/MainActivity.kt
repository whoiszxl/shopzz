package com.whoiszxl.mall.ui.activity

import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.whoiszxl.mall.R
import com.whoiszxl.mall.ui.fragment.HomeFragment
import com.whoiszxl.mall.ui.fragment.MeFragment
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    //双击返回键退出作计时用
    private var pressTime:Long = 0

    //Fragment 栈管理
    private val mStack = Stack<Fragment>()
    //主界面Fragment
    private val mHomeFragment by lazy { HomeFragment() }
    //商品分类Fragment
    private val mCategoryFragment by lazy { HomeFragment() }
    //购物车Fragment
    private val mCartFragment by lazy { HomeFragment() }
    //消息Fragment
    private val mMsgFragment by lazy { HomeFragment() }
    //"我的"Fragment
    private val mMeFragment by lazy { MeFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mBottomNavBar.checkMsgBadge(false)
        mBottomNavBar.checkCartBadge(20)

        initFragment()
        initBottomNav()
        changeFragment(0)
    }

    /**
     * 初始化底部导航切换事件
     */
    private fun initBottomNav() {
        mBottomNavBar.setTabSelectedListener(object :BottomNavigationBar.OnTabSelectedListener{
            override fun onTabReselected(position: Int) {
            }

            override fun onTabUnselected(position: Int) {
            }

            override fun onTabSelected(position: Int) {
                changeFragment(position)
            }

        })
        mBottomNavBar.checkMsgBadge(false)
    }

    /*
        切换Tab，切换对应的Fragment
     */
    private fun changeFragment(position: Int) {
        val manager = supportFragmentManager.beginTransaction()
        //遍历栈中的fragemtn，然后全部隐藏
        for (fragment in mStack){
            manager.hide(fragment)
        }
        //展示position下标的fragment
        manager.show(mStack[position])
        manager.commit()
    }

    private fun initFragment() {
        val manager = supportFragmentManager.beginTransaction()
        manager.add(R.id.mContainer,mHomeFragment)
        manager.add(R.id.mContainer,mCategoryFragment)
        manager.add(R.id.mContainer,mCartFragment)
        manager.add(R.id.mContainer,mMsgFragment)
        manager.add(R.id.mContainer,mMeFragment)
        manager.commit()

        mStack.add(mHomeFragment)
        mStack.add(mCategoryFragment)
        mStack.add(mCartFragment)
        mStack.add(mMsgFragment)
        mStack.add(mMeFragment)
    }
}
