package com.whoiszxl.base.ui.activity

import com.whoiszxl.base.presenter.BasePresenter
import com.whoiszxl.base.presenter.view.BaseView

open class BaseMvpActivity<T:BasePresenter<*>>:BaseActivity(),BaseView {
    override fun showLoading() {

    }

    override fun hideLoading() {
    }

    override fun onError() {
    }


    lateinit var mPresenter:T
}