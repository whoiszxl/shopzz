package com.whoiszxl.sms.impl;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.teaopenapi.models.Config;
import com.whoiszxl.entity.Signature;
import com.whoiszxl.entity.SmsChannelDTO;
import com.whoiszxl.sms.AbstractSmsService;
import com.whoiszxl.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 阿里云短信发送服务
 *
 * @author whoiszxl
 * @date 2022/5/30
 */
@Slf4j
public class AliyunSmsService extends AbstractSmsService {

    private Client client;

    public AliyunSmsService(SmsChannelDTO smsChannelDTO) {
        this.channel = smsChannelDTO;
    }



    @Override
    protected String sendSms(String mobile, Map<String, String> params, String signatureCode, String templateCode) {
        Signature signature = signatureService.getByCode(signatureCode);
        String code = templateService.getChannelCodeByTemplateCode(channel.getId(), templateCode);

        try{
            client = createClient(channel.getAccessKeyId(), channel.getAccessKeySecret());
            // 1.发送短信
            SendSmsRequest sendReq = new SendSmsRequest()
                    .setPhoneNumbers(mobile)
                    .setSignName(signature.getContent())
                    .setTemplateCode(code)
                    .setTemplateParam(JsonUtil.toJson(params));
            SendSmsResponse sendSmsResponse = client.sendSms(sendReq);
            String sendCode = sendSmsResponse.body.code;

            if(StringUtils.equals(sendCode, "OK")) {
                return JsonUtil.toJson(sendSmsResponse);
            }else {
                return failResponse("消息发送失败", JsonUtil.toJson(sendSmsResponse));
            }
        }catch (Exception e) {
            log.error("AliyunSmsService|阿里云短信发送失败", e);
            return failResponse("消息发送失败,本地发生异常", e.getMessage());
        }
    }

    @Override
    protected String sendSmsBatch(String[] mobiles, LinkedHashMap<String, String>[] params, String[] signatureCodeList, String[] templateCodeList) {
        return null;
    }

    public static Client createClient(String accessKeyId, String accessKeySecret) throws Exception {
        Config config = new Config();
        config.accessKeyId = accessKeyId;
        config.accessKeySecret = accessKeySecret;
        return new Client(config);
    }
}
