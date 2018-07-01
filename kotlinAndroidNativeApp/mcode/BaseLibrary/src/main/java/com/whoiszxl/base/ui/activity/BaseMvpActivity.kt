package com.whoiszxl.base.ui.activity

import android.os.Bundle
import com.whoiszxl.base.common.BaseApplication
import com.whoiszxl.base.injection.component.ActivityComponent
import com.whoiszxl.base.injection.component.DaggerActivityComponent
import com.whoiszxl.base.injection.module.ActivityModule
import com.whoiszxl.base.injection.module.LifecycleProviderModule
import com.whoiszxl.base.presenter.BasePresenter
import com.whoiszxl.base.presenter.view.BaseView
import com.whoiszxl.base.widgets.ProgressLoading
import es.dmoral.toasty.Toasty
import javax.inject.Inject

open abstract class BaseMvpActivity<T:BasePresenter<*>>:BaseActivity(),BaseView {


    @Inject
    lateinit var mPresenter:T

    lateinit var mActivityComponent: ActivityComponent

    private lateinit var mLoadingDialog:ProgressLoading

    override fun showLoading() {
        mLoadingDialog.showLoading()
    }

    override fun hideLoading() {
        mLoadingDialog.hideLoading()
    }

    override fun onError(text:String) {
        Toasty.error(this, text).show()
        return
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initActivityInjection()
        injectComponent()

        mLoadingDialog = ProgressLoading.create(this)
    }

    abstract fun injectComponent()

    private fun initActivityInjection() {
        mActivityComponent = DaggerActivityComponent.builder()
                .appComponent((application as BaseApplication).appComponent)
                .activityModule(ActivityModule(this))
                .lifecycleProviderModule(LifecycleProviderModule(this))
                .build()
    }
}