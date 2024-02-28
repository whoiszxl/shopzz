package com.whoiszxl.taowu.common.utils;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.lang.Assert;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import com.whoiszxl.taowu.common.constants.CommonConstants;
import com.whoiszxl.taowu.common.properties.RsaProperties;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author whoiszxl
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecureUtils {

    /**
     * 使用公钥加密并base64
     * @param data 需要加密的数据
     * @param publicKey 公钥
     * @return 加密后的值
     */
    public static String encryptByRsaPublicKey(String data, String publicKey) {
        return Base64.encode(SecureUtil.rsa(null, publicKey).encrypt(data, KeyType.PublicKey));
    }

    /**
     * 读取默认私钥进行解密
     * @param data 需要解密的数据
     * @return 解密结果
     */
    public static String decryptByRsaPrivateKey(String data) {
        String privateKey = RsaProperties.PRIVATE_KEY;
        Assert.notBlank(privateKey, "RSA私钥未配置");
        return decryptByRsaPrivateKey(data, privateKey);
    }

    /**
     * 通过私钥进行解密
     * @param data 需要解密的数据
     * @param privateKey 私钥
     * @return 解密的结果
     */
    public static String decryptByRsaPrivateKey(String data, String privateKey) {
        return new String(SecureUtil.rsa(privateKey, null).decrypt(Base64.decode(data), KeyType.PrivateKey));
    }

    public static String passwordMd5(String data, String salt) {
        return SecureUtil.md5(
                SecureUtil.md5(SecureUtil.md5(data) + salt) + salt
        );
    }


    public static void main(String[] args) {
        String banana = passwordMd5("123456", CommonConstants.PASSWORD_SALT);
        System.out.println(banana);

        String s = encryptByRsaPublicKey("123456", "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA183EzqDs126Mf7mQmnvZ" +
                "ni95tmZIeHImKUJaogYEMXXr2AioadOvGhbZA0ID5xgI1aSoAqoqQrDRvgk0Jur0" +
                "7NybtPVZtSrfMIR45uMcPEaUAuI8kNhmpWYduj6+zteKmTAF96JM0OgtIWe8TYYK" +
                "F6HL7Fmui7tZwbleVQn9o3BihmXbOckmlAU6UFsCBMU0Dk5LkTMBX/hLpE3Dr1Jp" +
                "IkmOzgO0gKyStbooy6ifDBxy5juSy7VMrc3pEoa/+mSs3ei0WqcArwt2hyySOvnA" +
                "bCAa9f2TR14eIRTuBEiH+SkAPN0+t5kl1nQNcxy+E3SVwYQF8Za6VzvXDhTo7Xux" +
                "uQIDAQAB");

        System.out.println(s);


        String s1 = decryptByRsaPrivateKey("ud4t9rR2y0sV35BeUXtw96HpgVMO9cukccm1UG4UE5wSIep1lDScmkO3kcPLoSF31ue2ORyz40HXawXEzepqGcIjFtAPCPf8PEpHOWCyuXmWN0h0gY7NpMf6Z/2T4aaoeljUoiM3d1uqfA8POoHkoyNP2zICcsIDiSpDPIB+vmWqRkQghN1bBP72VAe13yO0lqc3cofzMypo0pSH0E0xZ1KWHL68RjIvaEGNj4KzQKDZuZn6UuI2mUFeR8wQdpxWVYklcrbpSqYXHMf78e7ycY4npXs0DI1bSp6WzfPspip4K4HA2lBFtNkKE8XMd7YOm3o+DSsER1bdt1zldXH4jA==", "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDXzcTOoOzXbox/" +
                "uZCae9meL3m2Zkh4ciYpQlqiBgQxdevYCKhp068aFtkDQgPnGAjVpKgCqipCsNG+" +
                "CTQm6vTs3Ju09Vm1Kt8whHjm4xw8RpQC4jyQ2GalZh26Pr7O14qZMAX3okzQ6C0h" +
                "Z7xNhgoXocvsWa6Lu1nBuV5VCf2jcGKGZds5ySaUBTpQWwIExTQOTkuRMwFf+Euk" +
                "TcOvUmkiSY7OA7SArJK1uijLqJ8MHHLmO5LLtUytzekShr/6ZKzd6LRapwCvC3aH" +
                "LJI6+cBsIBr1/ZNHXh4hFO4ESIf5KQA83T63mSXWdA1zHL4TdJXBhAXxlrpXO9cO" +
                "FOjte7G5AgMBAAECggEAcs/bvB7rwrXi8/US1M29eqZBd+Q0/Demy96sf3T5+CKH" +
                "pjj8+MiRaSzsqgoOL32e/aMLa9eCgla2UAdvgqEQS5AXGxBJeNRlR9fCxvvfQsY9" +
                "6sbYeCtZePe6KyNEuqD78QIWuqfTfHV5xUL+cLQ2XFpNNuKDtX2ohCON52pYUgH3" +
                "ZYvdCoo/FZSYLxPRvpmBv5qGgK/YUng71k3GmeruXEhX2j4YScuL5RM0lSZbtW5N" +
                "Ek9mOMAXDKd2GirRmRf1aL0VFDXm+WhMqwEuU20NsNHKSDYK2dn68x330K3M9YQ1" +
                "6fwYXKkwGGvX68EqAwXuoP78r5AmuFlwPcI/P6u8nQKBgQD1QAa58FJGtSiS5Akn" +
                "qIP/h/qZd5GtBoIo18FppfIIzmqu5IJER5g9mP64sMAuNXrbWUf/llfCs38Tagrh" +
                "tSzpZwz7cCxbVxWNAlBTvI1302PaCmiAeKnxVFOqUX97wvvz4bjhE3iqRCKQEuV9" +
                "pKBwJeZsMyfozV1s9BktgC/vbwKBgQDhQ1Kbdx+QYMMiYLYzLt0MQIPyoQhAj1la" +
                "MR30jiBqnMm8SneHpTWBueQUVR/luF++W13A+oIVHxTKAGC0MWWtiRA0ow/Q0l4V" +
                "Iv13D5jk0xhJgrYkaE23XGBEb2ivJaehV5xC2e0nv6oDELiqsewImXPXLeNycjuv" +
                "bMrRhSpdVwKBgBhPIFuN1pnrmvn0YHPt4CeT8uMDVw0vd/dUa3dtW3BEBY8QV1kt" +
                "P4nbgGQOIP5zjpnvX5OV81z5Lb9e43U74BfCfybtF3h2SUyQziV+qwcVjPAv1+vk" +
                "CfRBx4k6SsiZqx+hYBHvPCakWkitltG9X6m+F/jhmTMaKAiEqXo5GodBAoGBAM8J" +
                "/IPtYfJmLROMxgmW+g4Hwf6f6SDFYD+YQ0sB0mOKxZQs8V2YB+y6uRnC/3U5F66y" +
                "9SGoXSUikw3yeO9FM4njHyTx9lhc8aIFrebfTAyMxJbT76rYvw1XdxePOi04s7CE" +
                "AJglCmbtL0pv9PrWWvWwbJsKjzEiMx9SI7uc5EozAoGAQJIaSs32Tj4Qu6Txy8WJ" +
                "1rhY76T2eeZWJdbnD/0+mtt6/8Jc/Ra2od3U7eGgB378il/GK4h9dO/jozXG0Z64" +
                "5Z0Ox15isqk3J0HeN6//Zp+qr5zrHAotEXgYdkHsuOq9RmtP7/s/BcmXp+pdEc2s" +
                "/VwFhG6a/FhU/3BUxdH4WeQ=");


        System.out.println(s1);
    }

}
