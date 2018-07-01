package com.whoiszxl.user.ui.activity

import android.os.Bundle
import android.view.View
import com.whoiszxl.base.ext.enable
import com.whoiszxl.base.ext.onClick
import com.whoiszxl.base.ui.activity.BaseMvpActivity
import com.whoiszxl.user.R
import com.whoiszxl.user.injection.component.DaggerUserComponent
import com.whoiszxl.user.injection.module.UserModule
import com.whoiszxl.user.presenter.ForgetPwdPresenter
import com.whoiszxl.user.presenter.view.ForgetPwdView
import kotlinx.android.synthetic.main.activity_forget_pwd.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/**
 * @author whoiszxl
 * 忘记密码activity
 */
class ForgetPwdActivity : BaseMvpActivity<ForgetPwdPresenter>(), ForgetPwdView, View.OnClickListener {



    override fun injectComponent() {
        //这个需要编辑一个UserComponent类，然后重新编译生成DaggerUserComponent,然后构建注入this类，才能让依赖注入生效
        //太麻烦了叭
        DaggerUserComponent.builder().activityComponent(mActivityComponent).userModule(UserModule()).build().inject(this)
        mPresenter.mView = this
    }

    /**
     * 处理忘记密码后的回调事件
     */
    override fun onForgetPwdResult(result: String) {
        toast(result)
        startActivity<ResetPwdActivity>(
                "mobile" to mMobileEt.text.toString(),
                "verifyCode" to mVerifyCodeEt.text.toString())
    }

    override fun onSendVerifyCodeResult(result: String) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_pwd)
        //从这里实例化这个注册Presenter
        //mPresenter = RegisterPresenter()
        //然后初始化Presenter中的mView，因为当前Activity实现了RegisterView，RegisterView又实现了BaseView，所以是同一类型

        //不需要实例化mPresenter了，依赖注入了
        //mPresenter.mView = this



        initView()

    }

    /**
     * 初始化视图
     */
    private fun initView() {

        mNextBtn.enable(mMobileEt, {isBtnEnable()})
        mNextBtn.enable(mVerifyCodeEt, {isBtnEnable()})

        mVerifyCodeBtn.onClick(this)
        //调用click事件
        mNextBtn.onClick(this)

    }

    override fun onClick(view: View) {
        when(view.id) {
            R.id.mVerifyCodeBtn -> {
                print("点击了忘记密码发送短信验证码")
                mPresenter.forgetpwd_verifycode(mMobileEt.text.toString())
                mVerifyCodeBtn.requestSendVerifyNumber()
            }
            R.id.mNextBtn -> {
                mPresenter.forgetPwd(mMobileEt.text.toString(), mVerifyCodeEt.text.toString())
            }
        }
    }

    /**
     * 判断注册页面的是个按钮是否为空
     */
    private fun isBtnEnable():Boolean{
        return mMobileEt.text.isNullOrEmpty().not() &&
                mVerifyCodeEt.text.isNullOrEmpty().not()
    }
}
