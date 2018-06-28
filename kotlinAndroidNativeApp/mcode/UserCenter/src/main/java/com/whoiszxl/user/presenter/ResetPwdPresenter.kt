package com.whoiszxl.user.presenter

import com.whoiszxl.base.ext.execute
import com.whoiszxl.base.presenter.BasePresenter
import com.whoiszxl.base.rx.BaseSubscriber
import com.whoiszxl.base.utils.NetWorkUtils
import com.whoiszxl.user.presenter.view.ForgetPwdView
import com.whoiszxl.user.presenter.view.RegisterView
import com.whoiszxl.user.presenter.view.ResetPwdView
import com.whoiszxl.user.service.UserService
import es.dmoral.toasty.Toasty
import javax.inject.Inject


class ResetPwdPresenter @Inject constructor():BasePresenter<ResetPwdView>() {

    /**
     * 通过依赖注入的方式将service传递进来，和spring的那个操作一样一样
     */
    @Inject
    lateinit var userService: UserService

    fun resetPwd(mobile:String, pwd:String){

        if(!checkNetWork()){
            return
        }

        mView.showLoading()
        userService.resetPwd(mobile, pwd)
                .execute(object:BaseSubscriber<Boolean>(mView){
                    override fun onNext(t: Boolean) {
                        if(t){
                            mView.onResetPwdResukt("验证成功")
                            mView.hideLoading()
                        }

                    }
                }, lifecycleProvider)

    }
}