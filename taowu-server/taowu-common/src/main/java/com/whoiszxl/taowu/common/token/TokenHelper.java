package com.whoiszxl.taowu.common.token;

import com.whoiszxl.taowu.common.token.entity.AppLoginMember;
import com.whoiszxl.taowu.common.token.entity.LoginMember;

/**
 * token helper接口
 * @author whoiszxl
 */
public interface TokenHelper {

    /**
     * 登录
     * @param loginMember 登录用户信息
     */
    void login(LoginMember loginMember);

    /**
     * C端用户登录
     * @param appLoginMember 登录用户信息
     */
    void appLogin(AppLoginMember appLoginMember);

    /**
     * 获取当前登录APP用户信息
     * @return 登录APP用户信息
     */
    AppLoginMember getAppLoginMember();


    /**
     * 获取当前登录用户信息
     * @return 登录用户信息
     */
    LoginMember getLoginMember();

    /**
     * 更新登录用户信息
     * @param loginMember 登录用户信息
     */
    void updateLoginMember(LoginMember loginMember);

    /**
     * 更新APP登录用户信息
     * @param appLoginMember 登录APP用户信息
     */
    void updateAppLoginMember(AppLoginMember appLoginMember);

    /**
     * 获取当前登录用户id
     * @return 用户id
     */
    Long getMemberId();


    /**
     * 获取当前APP登录用户id
     * @return 用户id
     */
    Long getAppMemberId();

    /**
     * 获取当前登录用户名
     * @return 用户名
     */
    String getUsername();

}
