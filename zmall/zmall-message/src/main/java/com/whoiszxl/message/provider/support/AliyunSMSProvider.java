package com.whoiszxl.message.provider.support;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.whoiszxl.common.entity.Result;
import com.whoiszxl.message.provider.SMSProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @description: 阿里云短信发送提供者服务
 * @author: whoiszxl
 * @create: 2020-01-03
 **/
@Slf4j
@Component
public class AliyunSMSProvider implements SMSProvider {

    //产品名称:云通信短信API产品,开发者无需替换
    static final String product = "Dysmsapi";
    //产品域名,开发者无需替换
    static final String domain = "dysmsapi.aliyuncs.com";

    @Value("${aliyun.sms.accessKeyId}")
    private String accessKeyId;

    @Value("${aliyun.sms.accessKeySecret}")
    private String accessKeySecret;

    @Value("${aliyun.sms.template_code}")
    private String templateCode;

    @Value("${aliyun.sms.sign_name}")
    private String signName;

    @Override
    public Result sendSingleMessage(String mobile, String templateCode, String params) throws Exception {
        DefaultProfile profile = DefaultProfile.getProfile("default", accessKeyId, accessKeySecret);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain(domain);
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "default");
        request.putQueryParameter("PhoneNumbers", mobile);
        request.putQueryParameter("SignName", signName);
        request.putQueryParameter("TemplateCode", templateCode);
        request.putQueryParameter("TemplateParam", params);
        try {
            CommonResponse response = client.getCommonResponse(request);
            return Result.success(response);
        } catch (ClientException e) {
            return Result.fail(e.getMessage());
        }
    }

    @Override
    public Result sendVerifyMessage(String mobile, String verifyCode) {
        try {
            return sendSingleMessage(mobile, this.templateCode, " {\"code\":\"" + verifyCode + "\"}");
        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }


    @Override
    public Result sendInternationalMessage(String content, String phone) throws Exception {
        return null;
    }
}