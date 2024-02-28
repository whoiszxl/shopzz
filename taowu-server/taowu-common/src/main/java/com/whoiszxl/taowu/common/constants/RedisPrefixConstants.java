package com.whoiszxl.taowu.common.constants;

/**
 * Redis Key 前缀常量
 *
 * @author whoiszxl
 */
public class RedisPrefixConstants {


    public interface Admin {
        String ADMIN_CAPTCHA_IMAGE_KEY = "admin:captcha:image:key";
    }

    public interface Member {

        String MEMBER_CAPTCHA_SMS = "member:captcha:sms";
        String MEMBER_CAPTCHA_SMS_SEPARATOR = "_";
        Integer SMS_TIMEOUT = 120;

    }

    public static String format(String... keys) {
        return String.join(":", keys);
    }

}
