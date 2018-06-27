package com.whoiszxl.user.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.whoiszxl.base.ui.activity.BaseMvpActivity
import com.whoiszxl.user.R
import com.whoiszxl.user.presenter.RegisterPresenter
import com.whoiszxl.user.presenter.view.RegisterView
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.toast

class RegisterActivity : BaseMvpActivity<RegisterPresenter>(), RegisterView {

    /**
     * 处理注册后的回调事件
     */
    override fun onRegisterResukt(result: Boolean) {
        toast("注册成功")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        //从这里实例化这个注册Presenter
        mPresenter = RegisterPresenter()
        //然后初始化Presenter中的mView，因为当前Activity实现了RegisterView，RegisterView又实现了BaseView，所以是同一类型
        mPresenter.mView = this

        //调用click事件
        mRegisterBtn.setOnClickListener {
            if(mPwdEt.text.toString() != mRePwdEt.text.toString()){
                toast("两次输入的密码不一致")
            }else{
                //调用Presenter的register方法，register里面又会铜鼓mView调用当前类里面的onRegisterResukt方法
                mPresenter.register(mMobileEt.text.toString(),mVerifyCodeEt.text.toString(), mPwdEt.text.toString())
            }
        }
    }
}
