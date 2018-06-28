package com.whoiszxl.user.ui.activity

import android.os.Bundle
import android.view.View
import com.whoiszxl.base.ext.enable
import com.whoiszxl.base.ext.onClick
import com.whoiszxl.base.ui.activity.BaseMvpActivity
import com.whoiszxl.user.R
import com.whoiszxl.user.injection.component.DaggerUserComponent
import com.whoiszxl.user.injection.module.UserModule
import com.whoiszxl.user.presenter.ResetPwdPresenter
import com.whoiszxl.user.presenter.view.ResetPwdView
import kotlinx.android.synthetic.main.activity_reset_pwd.*
import org.jetbrains.anko.toast

/**
 * @author whoiszxl
 * 忘记密码activity
 */
class ResetPwdActivity : BaseMvpActivity<ResetPwdPresenter>(), ResetPwdView {

    override fun injectComponent() {
        //这个需要编辑一个UserComponent类，然后重新编译生成DaggerUserComponent,然后构建注入this类，才能让依赖注入生效
        //太麻烦了叭
        DaggerUserComponent.builder().activityComponent(activityComponent).userModule(UserModule()).build().inject(this)
        mPresenter.mView = this
    }

    /**
     * 处理重置密码后的回调事件
     */
    override fun onResetPwdResukt(result: String) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_pwd)
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

        mConfirmBtn.enable(mPwdEt, {isBtnEnable()})
        mConfirmBtn.enable(mPwdConfirmEt, {isBtnEnable()})

        //调用click事件
        mConfirmBtn.onClick {
            if(mPwdEt.text.toString() != mPwdConfirmEt.text.toString()) {
                toast("两次输入密码不一致")
                return@onClick
            }
            mPresenter.resetPwd(intent.getStringExtra("mobile"), mPwdEt.text.toString())
        }

    }


    /**
     * 判断注册页面的是个按钮是否为空
     */
    private fun isBtnEnable():Boolean{
        return mConfirmBtn.text.isNullOrEmpty().not() &&
                mConfirmBtn.text.isNullOrEmpty().not()
    }
}
