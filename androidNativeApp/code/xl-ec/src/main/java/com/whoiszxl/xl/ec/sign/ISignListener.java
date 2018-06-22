package com.whoiszxl.xl.ec.sign;

/**
 * @author whoiszxl
 */
public interface ISignListener {

    /**
     * 登录成功回调
     */
    void onSignInSuccess();


    /**
     * 注册成功回调
     */
    void onSignUpSuccess();
}