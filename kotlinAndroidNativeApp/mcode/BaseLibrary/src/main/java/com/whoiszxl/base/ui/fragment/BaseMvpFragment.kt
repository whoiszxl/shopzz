package com.whoiszxl.base.ui.fragment

import android.os.Bundle
import com.whoiszxl.base.common.BaseApplication
import com.whoiszxl.base.injection.component.ActivityComponent
import com.whoiszxl.base.injection.component.DaggerActivityComponent
import com.whoiszxl.base.injection.module.ActivityModule
import com.whoiszxl.base.injection.module.LifecycleProviderModule
import com.whoiszxl.base.presenter.BasePresenter
import com.whoiszxl.base.presenter.view.BaseView
import javax.inject.Inject

open abstract class BaseMvpFragment<T:BasePresenter<*>>:BaseFragment(),BaseView {
    override fun showLoading() {

    }

    override fun hideLoading() {
    }

    override fun onError(text:String) {
    }

    @Inject
    lateinit var mPresenter:T

    lateinit var mActivityComponent: ActivityComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initActivityInjection()
        injectComponent()
    }

    abstract fun injectComponent()

    private fun initActivityInjection() {
        mActivityComponent = DaggerActivityComponent.builder()
                .appComponent((activity.application as BaseApplication).appComponent)
                .activityModule(ActivityModule(activity))
                .lifecycleProviderModule(LifecycleProviderModule(this))
                .build()
    }
}