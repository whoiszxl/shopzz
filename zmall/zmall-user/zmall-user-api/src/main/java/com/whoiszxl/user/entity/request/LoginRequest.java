package com.whoiszxl.user.entity.request;

import lombok.Data;

/**
 * @description: 登录请求类
 * @author: whoiszxl
 * @create: 2020-04-02
 **/
@Data
public class LoginRequest {

    /** 用户名 */
    private String username;

    /** 密码 */
    private String password;

    /** 用户输入的谷歌验证码 */
    private Integer googleCode;
}
