package com.whoiszxl.oauth;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: whoiszxl
 * @create: 2020-03-25
 **/
public class JwtTest {
    @Test
    public void createJWT(){
        //基于私钥生成jwt
        //1. 创建一个秘钥工厂
        //1: 指定私钥的位置
        ClassPathResource classPathResource = new ClassPathResource("zmall.jks");
        //2: 指定秘钥库的密码
        String keyPass = "zmall1020";
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(classPathResource, keyPass.toCharArray());

        //2. 基于工厂获取私钥
        String alias = "zmall";
        String password = "zmall1020";
        KeyPair keyPair = keyStoreKeyFactory.getKeyPair(alias, password.toCharArray());
        //将当前的私钥转换为rsa私钥
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();

        //3.生成jwt
        Map<String,String> map = new HashMap();
        map.put("company","moon");
        map.put("address","shenzhen");

        Jwt jwt = JwtHelper.encode(JSON.toJSONString(map), new RsaSigner(rsaPrivateKey));
        String jwtEncoded = jwt.getEncoded();
        System.out.println(jwtEncoded);
    }


    @Test
    public void parseJwt(){
        //基于公钥去解析jwt
        String jwt ="eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhZGRyZXNzIjoic2hlbnpoZW4iLCJjb21wYW55IjoibW9vbiJ9.G0z5-kAYxFV4h3eS_buaZMh3zKgnDulYb2HB8w6Kr9AcSGmYxkwfoolDuGv22NLFRywx1miluwu7rsvetk0R5p72k1jKjgfCSNMBbLRfWotoepSBnXddeIdfw5BEFd1t5lwUsLtqqjObnB-vsjUowIngfF3jXFoG0pD8bmRsZkJFry-kJC2BdTydpDfHA7NcC8PZk2joHMEEBvX4vaaIesIWQtabx-ixAt0eWFJLf3nXePVBZFCTKeli4NGggMDHLT5tiKLwcsJi1d3j90BBBX1yML_hM0DP8BKvDBaROZPuKu1qYQBPkU2X2qdZoukgtw5TdSrGO45kJuYOGNj9Rw";

        String publicKey ="-----BEGIN PUBLIC KEY-----MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAiAlHY2EqDrVEqKiyz05ZBudK8CAvMDWyAwGNP6uQUD0f23NFu37abPU3TbQPdQNA1HR08Yfiwge+MI47SJ61/bvwxwlXPUdPvHVMhIas6ETgftvgacIQTgjbyPj3FF4otR24ZimHWjQ/0fKkueqfvXn2+Bi0NiW97iSXaOkATtMa4z5/2oVPa6zW95gB/hRfBdRHiICqZamKRG/XyHf199eOb6cnwvR+IpkFBN6kOZ2/ew4J6YUPT/LwojlU2pyT7H1njskSvuw5NPv9DJ1yOKQueyvIY4lk20Py1P1j9P6ki0F2Xof+TzbhMyg0HJkIJSQWfNl1NaDHVxg25qJVLwIDAQAB-----END PUBLIC KEY-----";

        Jwt token = JwtHelper.decodeAndVerify(jwt, new RsaVerifier(publicKey));

        String claims = token.getClaims();
        System.out.println(claims);
    }
}
