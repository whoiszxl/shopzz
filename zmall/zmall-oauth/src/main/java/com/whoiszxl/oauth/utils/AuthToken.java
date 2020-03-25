package com.whoiszxl.oauth.utils;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: auth token对象
 * @author: whoiszxl
 * @create: 2020-03-25
 **/
@Data
public class AuthToken implements Serializable {

    /** 令牌token jwt */
    private String accessToken;

    /** 刷新token */
    private String refreshToken;

    /** jwt短令牌 */
    private String jti;
}
