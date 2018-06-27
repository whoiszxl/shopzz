package com.whoiszxl.base.ui.activity

import android.os.Bundle
import com.trello.rxlifecycle.components.support.RxAppCompatActivity
import com.whoiszxl.base.common.AppManager

open class BaseActivity: RxAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppManager.instance.addActivity(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        AppManager.instance.finishActivity(this)
    }
}