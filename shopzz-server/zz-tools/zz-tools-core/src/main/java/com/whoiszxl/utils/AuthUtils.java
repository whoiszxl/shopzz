package com.whoiszxl.utils;

import cn.dev33.satoken.stp.StpUtil;

/**
 * token工具类
 *
 * @author whoiszxl
 * @date 2022/1/24
 */
public class AuthUtils {

    /**
     * 获取当前用户的登录用户ID
     * @return
     */
    public static Long getMemberId() {
        return StpUtil.getLoginIdAsLong();
    }

    /**
     * 对memberId的用户进行登录操作
     * @param memberId 用户ID
     */
    public static void login(Long memberId) {
        StpUtil.login(memberId);
    }

    /**
     * 获取当前登录用户的token
     * @return token
     */
    public static String getToken() {
        return StpUtil.getTokenValue();
    }

    /**
     * 对token用户进行登出操作
     */
    public static void logout() {
        StpUtil.logout();
    }
}
