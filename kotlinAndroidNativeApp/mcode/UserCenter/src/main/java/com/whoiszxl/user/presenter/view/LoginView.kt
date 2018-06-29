package com.whoiszxl.user.presenter.view

import com.whoiszxl.base.presenter.view.BaseView
import com.whoiszxl.user.data.protocol.UserInfo

interface LoginView:BaseView {

    fun onLoginResult(result:UserInfo)
}