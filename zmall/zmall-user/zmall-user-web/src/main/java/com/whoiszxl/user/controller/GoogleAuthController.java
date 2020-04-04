package com.whoiszxl.user.controller;

import com.whoiszxl.common.constant.BooleanEnum;
import com.whoiszxl.common.entity.Result;
import com.whoiszxl.common.exception.ExceptionCatcher;
import com.whoiszxl.common.utils.GoogleAuthUtils;
import com.whoiszxl.user.config.TokenDecode;
import com.whoiszxl.user.entity.User;
import com.whoiszxl.user.entity.request.GoogleBindRequest;
import com.whoiszxl.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: 谷歌验证控制器
 * @author: whoiszxl
 * @create: 2020-04-03
 **/
@Api(value = "ZMALL-谷歌验证控制器", tags = "ZMALL-谷歌验证控制器")
@RestController
@RequestMapping("/google")
public class GoogleAuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenDecode tokenDecode;

    @Value("${message.isMock}")
    private Boolean isMock;


    @ApiOperation("生成谷歌验证码")
    @PostMapping("/generate")
    public Result generate() {
        String username = tokenDecode.getUsername();
        String key = GoogleAuthUtils.createKey();
        String qrCode = GoogleAuthUtils.getQrCode("ZMALL", username, key);
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("key", key);
        resultMap.put("qrCode", qrCode);
        return Result.success(resultMap);
    }


        @ApiOperation("绑定谷歌验证码接口")
    @PostMapping("/bind")
    public Result bind(@RequestBody GoogleBindRequest googleBindRequest){

        //获取当前用户的信息
        String username = tokenDecode.getUsername();
        User currentUser = userService.getById(username);

        if(currentUser.getGoogleKeySwitch().equals(BooleanEnum.IS_TRUE.getBool()) || StringUtils.isNotEmpty(currentUser.getGoogleKey())) {
            ExceptionCatcher.catchValidateEx(Result.fail("已绑定谷歌验证码"));
        }

        //校验缓存中的验证码
        boolean isSuccess = userService.checkVerifyCode(currentUser.getPhone(), googleBindRequest.getVerifyCode());
        if(!isSuccess) {
            ExceptionCatcher.catchValidateEx(Result.fail("验证码校验失败"));
        }

        //校验谷歌验证码是否输入正确
        isSuccess = GoogleAuthUtils.checkKey(googleBindRequest.getGoogleKey(), googleBindRequest.getGoogleCode());
        if(!isSuccess) {
            ExceptionCatcher.catchValidateEx(Result.fail("谷歌验证码校验失败"));
        }

        //更新字段
        currentUser.setGoogleKey(googleBindRequest.getGoogleKey());
        currentUser.setGoogleKeySwitch(BooleanEnum.IS_TRUE.getBool());
        currentUser.setUpdated(LocalDateTime.now());
        userService.updateById(currentUser);

        return Result.successMsg("绑定成功");
    }

}
