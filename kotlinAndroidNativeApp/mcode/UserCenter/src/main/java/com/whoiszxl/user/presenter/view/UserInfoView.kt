package com.whoiszxl.user.presenter.view

import com.whoiszxl.base.presenter.view.BaseView
import com.whoiszxl.user.data.protocol.UserInfo

interface UserInfoView:BaseView {


    /**
     * 获取上传凭证回调
     */
    fun onGetUploadTokenResult(result:String)

    /**
     * 编辑用户资料回调
     */
    fun onEditUserResult(result: UserInfo)

}