package com.whoiszxl.user.service.impl

import com.whoiszxl.base.data.protocol.BaseResp
import com.whoiszxl.base.ext.convert
import com.whoiszxl.base.ext.convertBoolean
import com.whoiszxl.base.rx.BaseException
import com.whoiszxl.base.rx.BaseFuncBoolean
import com.whoiszxl.user.data.protocol.UserInfo
import com.whoiszxl.user.data.repository.UserRepository
import com.whoiszxl.user.service.UserService
import rx.Observable
import rx.functions.Func1
import javax.inject.Inject

class UserServiceImpl @Inject constructor() : UserService {



    @Inject
    lateinit var repository: UserRepository

    /**
     * 注册服务接口
     */
    override fun register(mobile: String, pwd: String, verifyCode: String): Observable<Boolean> {

        return repository.register(mobile, pwd, verifyCode).convertBoolean()
    }

    /**
     * 发送验证码接口
     */
    override fun verifycode(mobile: String): Observable<Boolean> {
        return repository.verifycode(mobile).convertBoolean()
    }

    /**
     * 忘记密码验证码接口
     */
    override fun forgetpwdVerifycode(mobile: String): Observable<Boolean> {
        return repository.forgetpwd_verifycode(mobile).convertBoolean()
    }

    /**
     * 登录服务接口
     */
    override fun login(mobile: String, pwd: String, pushId: String): Observable<UserInfo> {
        return repository.login(mobile, pwd, pushId).convert()

    }

    /**
     * 忘记密码
     */
    override fun forgetPwd(mobile: String, verifyCode: String): Observable<Boolean> {
        return repository.forgetPwd(mobile,verifyCode).convertBoolean()
    }

    /**
     * 重置密码
     */
    override fun resetPwd(mobile: String, pwd: String, verifyCode: String): Observable<Boolean> {
        return repository.resetPwd(mobile, pwd, verifyCode).convertBoolean()
    }

    /**
     * 修改用户资料
     */
    override fun editUser(userIcon: String, userName: String, userGender: String, userSign: String): Observable<UserInfo> {
        return repository.editUser(userIcon,userName,userGender,userSign).convert()
    }


}