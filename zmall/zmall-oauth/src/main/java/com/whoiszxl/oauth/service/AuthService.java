package com.whoiszxl.oauth.service;

import com.whoiszxl.oauth.utils.AuthToken;

/**
 * @description:
 * @author: whoiszxl
 * @create: 2020-03-25
 **/
public interface AuthService {

    /**
     * oauth2 登录
     * @param username 用户名
     * @param password 密码
     * @param clientId 客户端ID
     * @param clientSecret 客户端秘钥
     * @return auth token对象
     */
    AuthToken login(String username, String password, String clientId, String clientSecret);
}
