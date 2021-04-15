package com.whoiszxl.authentication.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * 用户信息控制器
 *
 * @author whoiszxl
 * @date 2021/4/8
 */
@RestController
public class UserInfoController {

    @GetMapping("/userinfo")
    public Principal userInfo(Principal principal) {
        return principal;
    }
}
