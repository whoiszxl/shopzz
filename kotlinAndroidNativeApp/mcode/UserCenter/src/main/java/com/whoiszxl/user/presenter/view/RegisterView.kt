package com.whoiszxl.user.presenter.view

import com.whoiszxl.base.presenter.view.BaseView

interface RegisterView:BaseView {

    fun onRegisterResukt(result:String)

    fun onSendVerifyCodeResult(result: String)
}