package com.whoiszxl.base.presenter

import com.trello.rxlifecycle.LifecycleProvider
import com.whoiszxl.base.presenter.view.BaseView
import javax.inject.Inject

/**
 * @author whoiszxl
 */
open class BasePresenter<T:BaseView> {

    lateinit var mView:T

    @Inject
    lateinit var lifecycleProvider: LifecycleProvider<*>

}