package com.whoiszxl.provider.common

import com.alibaba.android.arouter.launcher.ARouter
import com.whoiszxl.base.utils.AppPrefsUtils
import com.whoiszxl.provider.router.RouterPath

/**
 * 判断是否登录
 * 感觉这样判断有问题，还需要优化
 */
fun isLogined():Boolean{
    return AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_SIGN).isNotEmpty()
}

/**
 * 如果已经登录，进行传入的方法处理
 * 如果没有登录，进入登录界面
 */
fun afterLogin(method:()->Unit){
    if (isLogined()){
        method()
    }else{
        //TODO ARouter跳转不了
        // ARouter.getInstance().build(RouterPath.UserCenter.PATH_LOGIN).navigation()
    }
}


