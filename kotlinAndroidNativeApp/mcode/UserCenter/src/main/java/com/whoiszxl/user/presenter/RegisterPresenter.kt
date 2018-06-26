package com.whoiszxl.user.presenter

import com.whoiszxl.base.ext.execute
import com.whoiszxl.base.presenter.BasePresenter
import com.whoiszxl.base.rx.BaseSubscriber
import com.whoiszxl.user.presenter.view.RegisterView
import com.whoiszxl.user.service.impl.UserServiceImpl


class RegisterPresenter:BasePresenter<RegisterView>() {

    fun register(mobile:String, verifyCode:String, pwd:String){

        val userService = UserServiceImpl()
        userService.register(mobile, verifyCode, pwd)
                .execute(object:BaseSubscriber<Boolean>(){
                    override fun onNext(t: Boolean) {
                        mView.onRegisterResukt(t)
                    }
                })

    }
}