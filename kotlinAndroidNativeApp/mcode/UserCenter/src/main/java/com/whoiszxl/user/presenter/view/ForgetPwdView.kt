package com.whoiszxl.user.presenter.view

import com.whoiszxl.base.presenter.view.BaseView

interface ForgetPwdView:BaseView {

    fun onForgetPwdResult(result:String)

    fun onSendVerifyCodeResult(result: String)
}