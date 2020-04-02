package com.whoiszxl.user.entity.request;

import lombok.Data;

/**
 * @description: 登录请求类
 * @author: whoiszxl
 * @create: 2020-04-02
 **/
@Data
public class LoginRequest {

    private String username;
    private String password;
}
