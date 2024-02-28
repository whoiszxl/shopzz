package com.whoiszxl.taowu.common.token.impl;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.whoiszxl.taowu.common.constants.LoginConstants;
import com.whoiszxl.taowu.common.utils.MyServletUtil;
import com.whoiszxl.taowu.common.token.TokenHelper;
import com.whoiszxl.taowu.common.token.entity.AppLoginMember;
import com.whoiszxl.taowu.common.token.entity.LoginMember;
import com.whoiszxl.taowu.common.utils.IpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * sa-token的helper
 * @author whoiszxl
 */
@Slf4j
@Component
@ConditionalOnProperty(name = "taowu.token-active", havingValue = "sa-token")
public class SaTokenHelper implements TokenHelper {


    @Override
    public void login(LoginMember loginMember) {
        if(loginMember == null) {
            return;
        }

        HttpServletRequest request = MyServletUtil.getRequest();
        loginMember.setIp(ServletUtil.getClientIP(request));
        loginMember.setLocation(IpUtils.getCityInfo(loginMember.getIp()));
        loginMember.setBrowser(MyServletUtil.getBrowser(request));

        StpUtil.login(loginMember.getId());
        loginMember.setToken(StpUtil.getTokenValue());
        SaHolder.getStorage().set(LoginConstants.LOGIN_ADMIN_MEMBER_KEY, loginMember);
        StpUtil.getTokenSession().set(LoginConstants.LOGIN_ADMIN_MEMBER_KEY, loginMember);
    }

    @Override
    public void appLogin(AppLoginMember appLoginMember) {
        if(appLoginMember == null) {
            return;
        }

        HttpServletRequest request = MyServletUtil.getRequest();
        appLoginMember.setIp(ServletUtil.getClientIP(request));
        appLoginMember.setLocation(IpUtils.getCityInfo(appLoginMember.getIp()));
        appLoginMember.setBrowser(MyServletUtil.getBrowser(request));

        StpUtil.login(appLoginMember.getId());
        appLoginMember.setToken(StpUtil.getTokenValue());
        SaHolder.getStorage().set(LoginConstants.LOGIN_APP_MEMBER_KEY, appLoginMember);
        StpUtil.getTokenSession().set(LoginConstants.LOGIN_APP_MEMBER_KEY, appLoginMember);
    }

    @Override
    public LoginMember getLoginMember() {
        LoginMember loginMember = (LoginMember) SaHolder.getStorage().get(LoginConstants.LOGIN_ADMIN_MEMBER_KEY);
        if(loginMember != null) {
            return loginMember;
        }
        loginMember = (LoginMember) StpUtil.getTokenSession().get(LoginConstants.LOGIN_ADMIN_MEMBER_KEY);
        SaHolder.getStorage().set(LoginConstants.LOGIN_ADMIN_MEMBER_KEY, loginMember);

        return loginMember;
    }

    @Override
    public AppLoginMember getAppLoginMember() {
        AppLoginMember appLoginMember = (AppLoginMember) SaHolder.getStorage().get(LoginConstants.LOGIN_APP_MEMBER_KEY);
        if(appLoginMember != null) {
            return appLoginMember;
        }
        appLoginMember = (AppLoginMember) StpUtil.getTokenSession().get(LoginConstants.LOGIN_APP_MEMBER_KEY);
        SaHolder.getStorage().set(LoginConstants.LOGIN_APP_MEMBER_KEY, appLoginMember);

        return appLoginMember;
    }

    @Override
    public void updateLoginMember(LoginMember loginMember) {
        SaHolder.getStorage().set(LoginConstants.LOGIN_ADMIN_MEMBER_KEY, loginMember);
        StpUtil.getTokenSession().set(LoginConstants.LOGIN_ADMIN_MEMBER_KEY, loginMember);
    }

    @Override
    public void updateAppLoginMember(AppLoginMember appLoginMember) {
        SaHolder.getStorage().set(LoginConstants.LOGIN_APP_MEMBER_KEY, appLoginMember);
        StpUtil.getTokenSession().set(LoginConstants.LOGIN_APP_MEMBER_KEY, appLoginMember);
    }

    @Override
    public Long getMemberId() {
        return getLoginMember().getId();
    }

    @Override
    public Long getAppMemberId() {
        return getAppLoginMember().getId();
    }

    @Override
    public String getUsername() {
        return getLoginMember().getUsername();
    }
}
