package com.whoiszxl.user.entity.request;

import lombok.Data;

/**
 * @description: 注册请求类
 * @author: whoiszxl
 * @create: 2020-04-02
 **/
@Data
public class RegisterRequest {

    private String mobile;

    private String code;

    private String password;

    private String rePassword;
}
