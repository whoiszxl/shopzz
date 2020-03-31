package com.whoiszxl.pay.entity;

import lombok.Data;

/**
 * @description: 支付宝支付配置参数
 * @author: whoiszxl
 * @create: 2020-03-31
 **/
@Data
public class AlipayConfigParam {

    //应用id
    public String appId;
    //私钥
    public String rsaPrivateKey;
    //异步回调通知
    public String notifyUrl;
    //同步回调通知
    public String returnUrl;
    //支付宝网关
    public String url;
    //编码方式 UTF-8
    public String charest;
    //格式JSON
    public String format;
    //ali公钥
    public String alipayPublicKey;
    //日志保存路径， 目前不用
    public String logPath;
    //RSA2 户生成签名字符串所使用的签名算法类型，目前支持RSA2和RSA，推荐使用RSA2
    public String signtype;
}
