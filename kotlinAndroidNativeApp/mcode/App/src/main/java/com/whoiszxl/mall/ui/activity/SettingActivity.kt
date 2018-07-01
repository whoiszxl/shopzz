package com.whoiszxl.mall.ui.activity

import android.os.Bundle
import com.whoiszxl.base.ext.onClick
import com.whoiszxl.base.ui.activity.BaseActivity
import com.whoiszxl.mall.R
import com.whoiszxl.user.utils.UserPrefsUtils
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        mUserProtocolTv.onClick {
            Toasty.success(this,"用户协议").show()
        }

        mFeedBackTv.onClick {
            Toasty.success(this,"反馈意见").show()
        }

        mAboutTv.onClick {
            Toasty.success(this,"关于").show()
        }

        //退出登录，清空本地用户数据
        mLogoutBtn.onClick {
            UserPrefsUtils.putUserInfo(null)
            finish()
        }

    }

}