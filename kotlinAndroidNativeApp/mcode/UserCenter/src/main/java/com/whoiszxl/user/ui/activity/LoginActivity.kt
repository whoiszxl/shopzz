package com.whoiszxl.user.ui.activity

import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.whoiszxl.base.ext.enable
import com.whoiszxl.base.ext.onClick
import com.whoiszxl.base.ui.activity.BaseMvpActivity
import com.whoiszxl.provider.router.RouterPath
import com.whoiszxl.user.R
import com.whoiszxl.user.data.protocol.UserInfo
import com.whoiszxl.user.injection.component.DaggerUserComponent
import com.whoiszxl.user.injection.module.UserModule
import com.whoiszxl.user.presenter.LoginPresenter
import com.whoiszxl.user.presenter.view.LoginView
import com.whoiszxl.user.utils.UserPrefsUtils
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/**
 * @author whoiszxl
 * 登录activity
 */
@Route(path = RouterPath.UserCenter.PATH_LOGIN)
class LoginActivity : BaseMvpActivity<LoginPresenter>(), LoginView, View.OnClickListener {



    private var pressTime:Long = 0

    override fun injectComponent() {
        //这个需要编辑一个UserComponent类，然后重新编译生成DaggerUserComponent,然后构建注入this类，才能让依赖注入生效
        //太麻烦了叭
        DaggerUserComponent.builder().activityComponent(mActivityComponent).userModule(UserModule()).build().inject(this)
        mPresenter.mView = this
    }

    /**
     * 处理登录成功后的回调事件
     */
    override fun onLoginResult(result: UserInfo) {
        toast("登录成功")
        UserPrefsUtils.putUserInfo(result)
        startActivity<UserInfoActivity>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

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

        mLoginBtn.enable(mMobileEt, {isBtnEnable()})
        mLoginBtn.enable(mPwdEt, {isBtnEnable()})

        mHeaderBar.getRightView().onClick(this)
        //调用click事件
        mLoginBtn.onClick(this)
        mForgetPwdTv.onClick(this)

    }


    override fun onClick(view: View) {
        when(view.id) {
            R.id.mRightTv -> { startActivity<RegisterActivity>() }
            R.id.mForgetPwdTv -> { startActivity<ForgetPwdActivity>() }
            R.id.mLoginBtn -> {
                mPresenter.login(mMobileEt.text.toString(), mPwdEt.text.toString(),"123666")
            }

        }
    }

    /**
     * 判断注册页面的是个按钮是否为空
     */
    private fun isBtnEnable():Boolean{
        return mMobileEt.text.isNullOrEmpty().not() &&
                mPwdEt.text.isNullOrEmpty().not()
    }
}
