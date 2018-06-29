package com.whoiszxl.user.utils

import com.whoiszxl.base.common.BaseConstant
import com.whoiszxl.base.utils.AppPrefsUtils
import com.whoiszxl.provider.common.ProviderConstant
import com.whoiszxl.user.data.protocol.UserInfo

/**
 * 本地存储用户相关信息
 */
object UserPrefsUtils {

    /*
        退出登录时，传入null,清空存储
     */
    fun putUserInfo(userInfo: UserInfo?) {
        AppPrefsUtils.putString(ProviderConstant.KEY_SP_USER_ID, userInfo?.id ?: "")
        AppPrefsUtils.putString(ProviderConstant.KEY_SP_USER_ICON, userInfo?.icon ?: "")
        AppPrefsUtils.putString(ProviderConstant.KEY_SP_USER_NAME, userInfo?.username ?: "")
        AppPrefsUtils.putString(ProviderConstant.KEY_SP_USER_MOBILE, userInfo?.phone ?: "")
        AppPrefsUtils.putString(ProviderConstant.KEY_SP_USER_SIGN, userInfo?.token ?: "")
    }
}
