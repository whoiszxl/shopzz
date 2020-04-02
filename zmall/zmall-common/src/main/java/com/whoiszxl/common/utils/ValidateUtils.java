package com.whoiszxl.common.utils;


import com.whoiszxl.common.entity.Result;
import com.whoiszxl.common.exception.ExceptionCatcher;
import org.apache.commons.lang3.StringUtils;

/**
 * @description: 校验参数有效性工具类
 * @author: whoiszxl
 * @create: 2020-01-07
 **/
public class ValidateUtils {


    /**
     * 校验密码是否一致及强度
     * @param password
     * @param rePassword
     */
    public static void checkPasswordEqual(String password, String rePassword) {
        if(!StringUtils.equals(password, rePassword)) {
            ExceptionCatcher.catchValidateEx(Result.fail("两次输入的密码不一致"));
        }
    }

    public static void checkPasswordLevel(String password) {
        CheckPasswordUtils.LEVEL passwordLevel = CheckPasswordUtils.getPasswordLevel(password);
        if(passwordLevel.equals(CheckPasswordUtils.LEVEL.EASY)) {
            ExceptionCatcher.catchValidateEx(Result.fail("密码过于简单"));
        }
    }

    /**
     *  校验手机格式
     * @param phone
     */
    public static void checkPhoneRegex(String phone) {
        if(!RegexUtils.checkPhone(phone)) {
            ExceptionCatcher.catchValidateEx(Result.fail("手机格式错误"));
        }
    }

}