package com.whoiszxl.utils;

import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.EnvironmentPBEConfig;

/**
 * 配置文件加密工具
 * 配置在yml里的敏感信息需要通过加密后再使用，保证系统安全。
 * 可以在jar包启动的时候在命令行指定运行秘钥。
 *
 * @author whoiszxl
 * @date 2021/3/17
 */
@Slf4j
public class EncryptPropertiesUtils {

    public static final String SECRET = "123456";

    /**
     * 加密
     *
     * @param secret 加密秘钥,需要在jar包运行的时候指定运行
     * @param text   需要加密的内容
     */
    public static void encrypt(String secret, String text) {
        StandardPBEStringEncryptor standardPBEStringEncryptor = new StandardPBEStringEncryptor();
        EnvironmentPBEConfig config = new EnvironmentPBEConfig();
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setPassword(secret);
        standardPBEStringEncryptor.setConfig(config);
        String encryptedText = standardPBEStringEncryptor.encrypt(text);
        log.info("加密前:" + text + "   加密后:" + encryptedText);
    }


    /**
     * 加密
     * @param text 需要加密的内容
     */
    public static void encrypt(String text) {
        encrypt(SECRET, text);
    }


    /**
     * 解密
     *
     * @param secret        加密秘钥,需要在jar包运行的时候指定运行
     * @param encryptedText 需要解密的内容
     */
    public static void decrypt(String secret, String encryptedText) {
        StandardPBEStringEncryptor standardPBEStringEncryptor = new StandardPBEStringEncryptor();
        EnvironmentPBEConfig config = new EnvironmentPBEConfig();
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setPassword(secret);
        standardPBEStringEncryptor.setConfig(config);
        String plainText = standardPBEStringEncryptor.decrypt(encryptedText);
        log.info("解密前:" + encryptedText + "   解密后:" + plainText);
    }



    public static void main(String[] args) {
        String dbPassword = "mall1020!!";
        String redisPassword = "zxl123456!!";

        encrypt(dbPassword);
        encrypt(redisPassword);

        //decrypt("your secret", "DI9iXNL4PUxC4zOhd4I00cbzB0TLicu9eb3RHNio4dDm6uinKLOhzuae0O73E6ImWCrcvSoc466PwyEkz5GP9kgUPcx+EGtSMFGiE4eLHfcQVz4FNP5cIA3AiFsnmClLLCPwrPwOcmi5ABG7xVfY4UyIrVAwBjZz9FvbZ4+2x2T1g0JH01x/EPC2J50xmqG6");
        //decrypt("your secret", "le89BOO33fezSsQl/4kzpg==");
        //decrypt("your secret", "K6vVvNk53bp61G9ncufyZm9qes75AwEO");
        //decrypt("your secret", "Tjb6kL/GtwmUingUiNQEoxRlEQaBSO7+mJ7iQvMgOAdYX3yU+185LPap1GfQLHNo");

    }
}