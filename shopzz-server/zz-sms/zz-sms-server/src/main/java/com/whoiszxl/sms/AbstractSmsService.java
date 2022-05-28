package com.whoiszxl.sms;

import com.whoiszxl.entity.SmsChannelDTO;
import com.whoiszxl.service.SignatureService;
import com.whoiszxl.service.TemplateService;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 实际发送短信的抽象类，各平台需对此进行继承实现
 *
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

    public String send(String mobile, Map<String, String> params, String signature, String template) {
        return this.sendSms(mobile, params, signature, template);
    }

    public String sendBatch(String[] mobiles, LinkedHashMap<String, String>[] params, String[] signatures, String[] templates) {
        return this.sendSmsBatch(mobiles, params, signatures, templates);
    }

    /**
     * 发送短信
     * @param mobile 手机号
     * @param params 参数
     * @param signature 签名
     * @param template 模板
     * @return
     */
    protected abstract String sendSms(String mobile, Map<String, String> params, String signature, String template);

    /**
     * 批量发送短信
     * @param mobiles
     * @param params
     * @param signatures
     * @param templates
     * @return
     */
    protected abstract String sendSmsBatch(String[] mobiles, LinkedHashMap<String, String>[] params, String[] signatures, String[] templates);

    protected String failResponse(String msg, String response) {
        return "FAIL@#@" + msg + "@#@" + response;
    }
}
