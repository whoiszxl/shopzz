package com.whoiszxl.oauth.controller;

import com.whoiszxl.common.constant.BooleanEnum;
import com.whoiszxl.common.entity.Result;
import com.whoiszxl.common.exception.ExceptionCatcher;
import com.whoiszxl.common.utils.GoogleAuthUtils;
import com.whoiszxl.oauth.service.AuthService;
import com.whoiszxl.oauth.utils.AuthToken;
import com.whoiszxl.oauth.utils.CookieUtil;
import com.whoiszxl.user.entity.User;
import com.whoiszxl.user.entity.request.LoginRequest;
import com.whoiszxl.user.feign.UserFeign;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
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


    @Resource
    private UserFeign userFeign;


    public static void main(String[] args) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode = passwordEncoder.encode("123456");
        System.out.println(encode);

        boolean matches = passwordEncoder.matches("changgou", "$2a$10$Yvkp3xzDcri6MAsPIqnzzeGBHez1QZR3A079XDdmNU4R725KrkXi2");
        System.out.println(matches);
    }

    @RequestMapping("/login")
    @ResponseBody
    public Result login(@RequestBody LoginRequest loginRequest) {
        //1, 校验参数
        if(StringUtils.isEmpty(loginRequest.getUsername())) {
            ExceptionCatcher.catchValidateEx(Result.fail("用户名输入不能为空"));
        }
        if (StringUtils.isEmpty(loginRequest.getPassword())){
            ExceptionCatcher.catchValidateEx(Result.fail("密码输入不能为空"));
        }

        //2.校验谷歌验证码
        User userInfo = userFeign.findUserInfo(loginRequest.getUsername());
        if(userInfo.getGoogleKeySwitch().equals(BooleanEnum.IS_TRUE.getBool())) {
            boolean isSuccess = GoogleAuthUtils.checkKey(userInfo.getGoogleKey(), loginRequest.getGoogleCode());
            if(!isSuccess) {
                ExceptionCatcher.catchValidateEx(Result.fail("谷歌验证失败"));
            }
        }

        //3. 申请令牌
        AuthToken authToken = authService.login(loginRequest.getUsername(), loginRequest.getPassword(), clientId, clientSecret);

        return Result.success(authToken);
    }
}
