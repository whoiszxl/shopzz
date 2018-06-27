package com.whoiszxl.user.presenter

import com.whoiszxl.base.ext.execute
import com.whoiszxl.base.presenter.BasePresenter
import com.whoiszxl.base.rx.BaseSubscriber
import com.whoiszxl.user.presenter.view.RegisterView
import com.whoiszxl.user.service.UserService
import javax.inject.Inject


class RegisterPresenter @Inject constructor():BasePresenter<RegisterView>() {

    /**
     * 通过依赖注入的方式将service传递进来，和spring的那个操作一样一样
     */
    @Inject
    lateinit var userService: UserService

    fun register(mobile:String, verifyCode:String, pwd:String){
        userService.register(mobile, verifyCode, pwd)
                .execute(object:BaseSubscriber<Boolean>(){
                    override fun onNext(t: Boolean) {
                        if(t){
                            mView.onRegisterResukt("注册成功")
                        }

                    }
                }, lifecycleProvider)

    }
}