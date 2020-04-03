package com.whoiszxl.common.utils;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;

/**
 * @description: 谷歌验证工具类
 * @author: whoiszxl
 * @create: 2020-04-02
 **/
public class GoogleAuthUtils {


    /**
     * 创建谷歌验证码
     * @return 创建好的谷歌验证码
     */
    public static String createKey() {
        GoogleAuthenticator gAuth = new GoogleAuthenticator();
        final GoogleAuthenticatorKey key = gAuth.createCredentials();
        return key.getKey();
    }


    /**
     * 获取谷歌验证码扫描用的二维码
     * @param typeName 类型名称，一般为公司品牌名，如：zmall
     * @param username 用户名，用户扫描二维码后绑定格式为[typeName:username],方便用户区分多个google验证码
     * @param secret 秘钥，储存在用户表中
     * @return
     */
    public static String getQrCode(String typeName, String username, String secret) {
        return "otpauth://totp/" + typeName + ":" + username + "?secret=" + secret;
    }

    /**
     * 校验谷歌验证码
     * @param key 用户数据库中储存的key
     * @param inputCode 用户前端输入的key
     * @return 是否校验成功
     */
    public static boolean checkKey(String key, Integer inputCode) {
        GoogleAuthenticator gAuth = new GoogleAuthenticator();
        return gAuth.authorize(key, inputCode);
    }

    public static void main(String[] args) {
//        String key = createKey();
//        System.out.println(key); // 44KUJYFVAEIVYUCY

        boolean b = checkKey("44KUJYFVAEIVYUCY", 249483);
        System.out.println(b);
    }

}
