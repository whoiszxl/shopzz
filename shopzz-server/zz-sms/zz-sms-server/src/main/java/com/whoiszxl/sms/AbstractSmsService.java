package com.whoiszxl.sms;

import com.whoiszxl.entity.SmsChannelDTO;
import com.whoiszxl.service.SignatureService;
import com.whoiszxl.service.TemplateService;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 实际发送短信的抽象类，各平台需对此进行继承实现
 * 继承
 * @author whoiszxl
 * @date 2022/5/28
 */
public abstract class AbstractSmsService {
    
    protected SmsChannelDTO channel;

    protected SignatureService signatureService;

    protected TemplateService templateService;

    public SmsChannelDTO getChannel() {
        return channel;
    }

    public String send(String mobile, Map<String, String> params, String signatureCode, String templateCode) {
        return this.sendSms(mobile, params, signatureCode, templateCode);
    }

    public String sendBatch(String[] mobiles, LinkedHashMap<String, String>[] params, String[] signatureCodeList, String[] templateCodeList) {
        return this.sendSmsBatch(mobiles, params, signatureCodeList, templateCodeList);
    }

    /**
     * 发送短信
     * @param mobile 手机号
     * @param params 参数
     * @param signatureCode 签名
     * @param templateCode 模板
     * @return
     */
    protected abstract String sendSms(String mobile, Map<String, String> params, String signatureCode, String templateCode);

    /**
     * 批量发送短信
     * @param mobiles
     * @param params
     * @param signatureCodeList
     * @param templateCodeList
     * @return
     */
    protected abstract String sendSmsBatch(String[] mobiles, LinkedHashMap<String, String>[] params, String[] signatureCodeList, String[] templateCodeList);

    protected String failResponse(String msg, String response) {
        return "FAIL@#@" + msg + "@#@" + response;
    }
}
