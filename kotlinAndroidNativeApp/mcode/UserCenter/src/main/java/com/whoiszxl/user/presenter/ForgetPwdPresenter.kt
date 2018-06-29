package com.whoiszxl.user.presenter

import com.whoiszxl.base.ext.execute
import com.whoiszxl.base.presenter.BasePresenter
import com.whoiszxl.base.rx.BaseSubscriber
import com.whoiszxl.user.presenter.view.ForgetPwdView
import com.whoiszxl.user.service.UserService
import javax.inject.Inject


class ForgetPwdPresenter @Inject constructor():BasePresenter<ForgetPwdView>() {

    /**
     * 通过依赖注入的方式将service传递进来，和spring的那个操作一样一样
     */
    @Inject
    lateinit var userService: UserService

    fun forgetPwd(mobile:String, verifyCode:String){

        if(!checkNetWork()){
            return
        }

        mView.showLoading()
        userService.forgetPwd(mobile, verifyCode)
                .execute(object:BaseSubscriber<Boolean>(mView){
                    override fun onNext(t: Boolean) {
                        if(t){
                            mView.onForgetPwdResult("验证成功")
                            mView.hideLoading()
                        }else{
                            mView.onForgetPwdResult("验证失败")
                            return
                        }

                    }
                }, lifecycleProvider)
    }


    fun resetPwd(mobile:String, password:String, verifyCode:String){

        if(!checkNetWork()){
            return
        }

        mView.showLoading()
        userService.resetPwd(mobile, password, verifyCode)
                .execute(object:BaseSubscriber<Boolean>(mView){
                    override fun onNext(t: Boolean) {
                        if(t){
                            mView.onForgetPwdResult("修改成功")
                            mView.hideLoading()
                        }
                    }
                }, lifecycleProvider)
    }

    fun forgetpwd_verifycode(mobile: String) {
        if(!checkNetWork()){
            return
        }
        userService.forgetpwdVerifycode(mobile)
                .execute(object:BaseSubscriber<Boolean>(mView){
                    override fun onNext(t: Boolean) {
                        if(t) {
                            mView.onSendVerifyCodeResult("验证码发送成功")
                        }
                    }

                    override fun onError(e: Throwable?) {
                        mView.onSendVerifyCodeResult("验证码发送失败")
                        return
                    }
                },lifecycleProvider)
    }
}