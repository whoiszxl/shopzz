package com.whoiszxl.user.entity.request;

import lombok.Data;

/**
 * @description: 谷歌绑定请求类
 * @author: whoiszxl
 * @create: 2020-04-02
 **/
@Data
public class GoogleBindRequest {

    private String googleKey;

    private Integer googleCode;

    private String verifyCode;

}
