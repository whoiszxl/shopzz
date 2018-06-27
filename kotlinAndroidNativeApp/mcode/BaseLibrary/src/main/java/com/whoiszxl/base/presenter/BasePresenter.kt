package com.whoiszxl.base.presenter

import android.content.Context
import com.trello.rxlifecycle.LifecycleProvider
import com.whoiszxl.base.presenter.view.BaseView
import com.whoiszxl.base.utils.NetWorkUtils
import javax.inject.Inject

/**
 * @author whoiszxl
 */
open class BasePresenter<T:BaseView> {

    lateinit var mView:T

    @Inject
    lateinit var lifecycleProvider: LifecycleProvider<*>

    @Inject
    lateinit var context: Context

    fun checkNetWork():Boolean {
        if(NetWorkUtils.isNetWorkAvailable(context)){
            return true
        }
        mView.onError("网络不可用")
        return false
    }

}