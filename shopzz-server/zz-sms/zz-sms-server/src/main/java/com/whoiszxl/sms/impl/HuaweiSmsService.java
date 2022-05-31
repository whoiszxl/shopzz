package com.whoiszxl.sms.impl;

import com.aliyun.dysmsapi20170525.Client;
import com.whoiszxl.entity.SmsChannelDTO;
import com.whoiszxl.sms.AbstractSmsService;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 华为云短信发送服务
 *
 * @author whoiszxl
 * @date 2022/5/30
 */
@Slf4j
public class HuaweiSmsService extends AbstractSmsService {

    private Client client;

    public HuaweiSmsService(SmsChannelDTO smsChannelDTO) {
        this.channel = smsChannelDTO;
    }



    @Override
    protected String sendSms(String mobile, Map<String, String> params, String signatureCode, String templateCode) {
        return "OK";
    }

    @Override
    protected String sendSmsBatch(String[] mobiles, LinkedHashMap<String, String>[] params, String[] signatureCodeList, String[] templateCodeList) {
        return null;
    }

}
