package com.whoiszxl.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.generator.config.TemplateType;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.cqrs.command.SmsSendCommand;
import com.whoiszxl.entity.*;
import com.whoiszxl.enums.SmsNeedAuthEnum;
import com.whoiszxl.enums.SmsTemplateTypeEnum;
import com.whoiszxl.enums.StatusEnum;
import com.whoiszxl.exception.ExceptionCatcher;
import com.whoiszxl.service.*;
import com.whoiszxl.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 短信发送服务接口实现
 *
 * @author whoiszxl
 * @date 2022/5/26
 */
@Service
public class SmsSendServiceImpl implements SmsSendService {

    @Autowired
    private PlatformService platformService;

    @Autowired
    private BlacklistService blacklistService;

    @Autowired
    private TemplateService templateService;

    @Autowired
    private SignatureService signatureService;
    
    @Autowired
    private TimingPushService timingPushService;

    @Autowired
    private ReceiveLogService receiveLogService;

    @Autowired
    private ChannelService channelService;

    @Autowired
    private IdWorker idWorker;

    @Override
    public void send(SmsSendCommand smsSendCommand) {
        //1. 校验定时发送时间
        boolean checkFlag = checkSendTime(smsSendCommand.getSendTime());
        AssertUtils.isTrue(checkFlag, "定时发送时间需要在当前时间一分钟后");

        //2. 校验平台是否注册
        Platform platform = checkPlatform(smsSendCommand.getAccessKeyId());

        //3. 校验平台是否需要鉴权
        if(platform.getNeedAuth().equals(SmsNeedAuthEnum.NEED.getCode())) {
            checkFlag = checkAuth(smsSendCommand.getTimestamp(), smsSendCommand.getAccessKeyId(), platform.getAccessKeySecret(), smsSendCommand.getEncryption());
            AssertUtils.isTrue(checkFlag, "鉴权失败");
        }

        sendSmsMessage(smsSendCommand, platform);
    }

    /**
     * 发送短信
     * @param smsSendCommand
     * @param platform
     */
    private void sendSmsMessage(SmsSendCommand smsSendCommand, Platform platform) {
        //1. 校验手机号是否正确
        String mobile = smsSendCommand.getMobile();
        boolean checkFlag = RegexUtils.checkMobile(mobile);
        AssertUtils.isTrue(checkFlag, "手机号不正确");

        //2. 校验手机号是否在黑名单
        long count = blacklistService.count(Wrappers.<Blacklist>lambdaQuery().eq(Blacklist::getMobile, mobile));
        AssertUtils.isTrue(count < 1, "无法发送黑名单手机");

        //3.校验签名和模板
        List<Long> configs = checkTemplateAndSignature(smsSendCommand.getTemplate(), smsSendCommand.getSignature());

        //3. 校验参数
        Template template = checkParams(smsSendCommand.getTemplate(), smsSendCommand.getParams());

        //4. 调用接口发送
        pushSmsMessage(template, smsSendCommand, platform, configs, "");
    }

    private List<Long> checkTemplateAndSignature(String templateCode, String signatureCode) {
        Template template = templateService.getOne(Wrappers.<Template>lambdaQuery().eq(Template::getCode, templateCode));
        AssertUtils.isTrue(template != null, "模板不存在");
        Signature signature = signatureService.getOne(Wrappers.<Signature>lambdaQuery().eq(Signature::getCode, signatureCode));
        AssertUtils.isTrue(signature != null, "签名不存在");

        List<Channel> channelList = channelService.listByTemplateAndSignature(template.getId(), signature.getId());
        AssertUtils.isTrue(CollectionUtil.isNotEmpty(channelList), "没有支持此模板和签名的通道");

        return channelList.stream().map(item -> item.getId()).collect(Collectors.toList());
    }

    /**
     * 根据短信模板进行分发，定时消息入库，实时消息发送到RocketMQ
     * @param template
     * @param smsSendCommand
     * @param platform
     */
    private void pushSmsMessage(Template template, SmsSendCommand smsSendCommand, Platform platform, List<Long> configs, String businessInfo) {
        ReceiveLog receiveLog = new ReceiveLog();
        receiveLog.setId(idWorker.nextId());
        long start = System.currentTimeMillis();

        try {
            String sendTime = smsSendCommand.getSendTime();
            String paramsJson = JsonUtil.toJson(smsSendCommand);

            //定时消息入库
            if(StringUtils.isNotEmpty(sendTime)){
                TimingPush timingPush = new TimingPush();
                timingPush.setMobile(smsSendCommand.getMobile());
                timingPush.setTemplate(smsSendCommand.getTemplate());
                timingPush.setSignature(smsSendCommand.getSignature());
                timingPush.setTiming(DateUtils.timingToLocalDateTime(sendTime));
                timingPush.setRequest(paramsJson);
                timingPushService.save(timingPush);
            }else{
                //实时发送,发送到RocketMQ
                if(template.getType() == SmsTemplateTypeEnum.VERIFICATION.getCode()){

                }else if(template.getType() == SmsTemplateTypeEnum.PROMOTION.getCode()){

                }
            }
            receiveLog.setStatus(1);
        }catch (Exception e){
            receiveLog.setStatus(0);
            receiveLog.setError(e.getMessage());
        }finally {
            receiveLog.setPlatformId(platform.getId());
            receiveLog.setPlatformName(platform.getName());
            receiveLog.setChannelIds(StringUtils.join(configs, ","));
            receiveLog.setTemplate(smsSendCommand.getTemplate());
            receiveLog.setSignature(smsSendCommand.getSignature());
            receiveLog.setMobile(smsSendCommand.getMobile());
            receiveLog.setRequest(JSON.toJSONString(smsSendCommand.getParams()));
            receiveLog.setTimeConsuming(System.currentTimeMillis() - start);
            receiveLog.setBusinessInfo(businessInfo);
            receiveLogService.save(receiveLog);
        }
    }

    /**
     * 校验参数
     * @param template
     * @param params
     * @return
     */
    private Template checkParams(String templateCode, Map<String, String> params) {
        Template template = templateService.getCacheByTemplateCode(templateCode);
        String content = TemplateUtils.renderString(template.getContent(), params);
        if (content.indexOf("${") > 0) {
            ExceptionCatcher.catchValidateEx(ResponseResult.buildError("参数不匹配"));
        }
        return template;
    }

    /**
     * 鉴权
     * @param timestamp
     * @param accessKeyId
     * @param accessKeySecret
     * @param encryption
     */
    private boolean checkAuth(String timestamp, String accessKeyId, String accessKeySecret, String encryption) {
        String encodeStr = SmsEncryptionUtils.encode(timestamp, accessKeyId, accessKeySecret);
        return encodeStr.equals(encryption);
    }

    /**
     * 检查平台是否有效
     * @param accessKeyId 秘钥id
     * @return
     */
    private Platform checkPlatform(String accessKeyId) {
        Platform platform = platformService.getPlatformByCache(accessKeyId);
        AssertUtils.isTrue(platform != null, "平台未添加");
        AssertUtils.isTrue(StatusEnum.OPEN.getCode().equals(platform.getStatus()), "平台未开启");
        return platform;
    }

    /**
     * 检查定时发送时间，如果发送时间在当前时间一分钟内就不允许发送了
     * @param sendTime
     * @return
     */
    private boolean checkSendTime(String sendTime) {
        if(StringUtils.isNotBlank(sendTime)) {
            LocalDateTime localSendTime = LocalDateTime.parse(sendTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            LocalDateTime localNowTime = LocalDateTime.now().plusMinutes(1L).withSecond(0).minusSeconds(0).withNano(0);
            if(localSendTime.isBefore(localNowTime)) {
                return false;
            }
        }
        return true;
    }
}
