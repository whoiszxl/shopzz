package com.whoiszxl.oauth.controller;

import com.whoiszxl.common.entity.Result;
import com.whoiszxl.oauth.service.AuthService;
import com.whoiszxl.oauth.utils.AuthToken;
import com.whoiszxl.oauth.utils.CookieUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * @description:
 * @author: whoiszxl
 * @create: 2020-03-25
 **/
@Controller
@RequestMapping("/oauth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Value("${auth.clientId}")
    private String clientId;

    @Value("${auth.clientSecret}")
    private String clientSecret;

    @Value("${auth.cookieDomain}")
    private String cookieDomain;

    @Value("${auth.cookieMaxAge}")
    private int cookieMaxAge;

    @RequestMapping("/toLogin")
    public String toLogin(@RequestParam(value = "FROM",required = false,defaultValue = "") String from, Model model){
        model.addAttribute("from",from);
        return "login";
    }

    public static void main(String[] args) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode = passwordEncoder.encode("123456");
        System.out.println(encode);
    }

    @RequestMapping("/login")
    @ResponseBody
    public Result login(String username, String password, HttpServletResponse response) {
        //1, 校验参数
            if(StringUtils.isEmpty(username)) {
            throw new RuntimeException("请输入用户名");
        }
        if (StringUtils.isEmpty(password)){
            throw new RuntimeException("请输入密码");
        }

        //2. 申请令牌
        AuthToken authToken = authService.login(username, password, "changgou", "changgou");

        //3. 存入cookie
        this.saveJtiToCookie(authToken.getJti(),response);

        return Result.success(authToken);
    }


    //将令牌的断标识jti存入到cookie中
    private void saveJtiToCookie(String jti, HttpServletResponse response) {
        CookieUtil.addCookie(response,cookieDomain,"/","uid",jti,cookieMaxAge,false);
    }
}
