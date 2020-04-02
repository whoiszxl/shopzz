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
