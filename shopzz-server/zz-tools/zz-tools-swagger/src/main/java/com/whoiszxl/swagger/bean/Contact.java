package com.whoiszxl.swagger.bean;

import lombok.Data;

/**
 * 联系人信息
 *
 * @author whoiszxl
 * @date 2021/11/30
 */
@Data
public class Contact {
    /**
     * 联系人
     **/
    private String name = "whoiszxl";
    /**
     * 联系人url
     **/
    private String url = "http://whoiszxl.com";
    /**
     * 联系人email
     **/
    private String email = "whoiszxl@gmail.com";
}