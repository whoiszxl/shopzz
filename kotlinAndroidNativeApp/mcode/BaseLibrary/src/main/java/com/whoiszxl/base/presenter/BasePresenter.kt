package com.whoiszxl.base.presenter

import com.whoiszxl.base.presenter.view.BaseView

/**
 * @author whoiszxl
 */
open class BasePresenter<T:BaseView> {

    lateinit var mView:T

}