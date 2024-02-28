package com.whoiszxl.taowu.admin.service;

/**
 * @author whoiszxl
 */
public interface LoginService {

    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @return token
     */
    String login(String username, String password);
}
