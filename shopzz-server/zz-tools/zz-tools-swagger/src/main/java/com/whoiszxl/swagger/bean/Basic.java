package com.whoiszxl.swagger.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户认证类
 *
 * @author whoiszxl
 * @date 2021/11/30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Basic {

    private Boolean enable = false;
    private String username = "seesee";
    private String password = "seesee";
}
