package com.whoiszxl.user.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.whoiszxl.base.common.AppManager
import com.whoiszxl.base.ext.onClick
import com.whoiszxl.base.ui.activity.BaseMvpActivity
import com.whoiszxl.base.widgets.VerifyButton
import com.whoiszxl.user.R
import com.whoiszxl.user.injection.component.DaggerUserComponent
import com.whoiszxl.user.injection.module.UserModule
import com.whoiszxl.user.presenter.RegisterPresenter
import com.whoiszxl.user.presenter.view.RegisterView
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.toast

class RegisterActivity : BaseMvpActivity<RegisterPresenter>(), RegisterView {

    private var pressTime:Long = 0

    override fun injectComponent() {
        //这个需要编辑一个UserComponent类，然后重新编译生成DaggerUserComponent,然后构建注入this类，才能让依赖注入生效
        //太麻烦了叭
        DaggerUserComponent.builder().activityComponent(activityComponent).userModule(UserModule()).build().inject(this)
        mPresenter.mView = this
    }

    /**
     * 处理注册后的回调事件
     */
    override fun onRegisterResukt(result: String) {
        toast(result)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        //从这里实例化这个注册Presenter
        //mPresenter = RegisterPresenter()
        //然后初始化Presenter中的mView，因为当前Activity实现了RegisterView，RegisterView又实现了BaseView，所以是同一类型

        //不需要实例化mPresenter了，依赖注入了
        //mPresenter.mView = this

        //调用click事件
        mRegisterBtn.onClick{
            toast(mMobileEt.text.toString())
            if(mPwdEt.text.toString() != mRePwdEt.text.toString()){
                toast("两次输入的密码不一致")
            }else{
                //调用Presenter的register方法，register里面又会铜鼓mView调用当前类里面的onRegisterResukt方法
                mPresenter.register(mMobileEt.text.toString(),mVerifyCodeEt.text.toString(), mPwdEt.text.toString())
            }
        }

        mGetVerifyCodeBtn.onClick {
            mGetVerifyCodeBtn.requestSendVerifyNumber()
        }


    }

    override fun onBackPressed() {
        val time = System.currentTimeMillis()
        if(time - pressTime > 2000) {
            toast("再按一次返回键退出XLMALL")
            pressTime = time
        }else{
            AppManager.instance.exitApp(this)
        }
    }

}
