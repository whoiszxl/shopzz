package com.whoiszxl.factory;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import com.alibaba.fastjson.JSON;
import com.whoiszxl.cqrs.command.SmsSendCommand;
import com.whoiszxl.entity.ManualTask;
import com.whoiszxl.entity.SendLog;
import com.whoiszxl.service.ManualTaskService;
import com.whoiszxl.service.SendLogService;
import com.whoiszxl.sms.AbstractSmsService;
import com.whoiszxl.utils.IdWorker;
import com.whoiszxl.utils.JsonUtil;
import com.whoiszxl.utils.RedisUtils;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * SMS短信发送工厂
 *
 * @author whoiszxl
 * @date 2022/5/28
 */
@Slf4j
@Component
public class SmsFactory {

    @Autowired
    private SmsConnectLoader smsConnectLoader;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private ManualTaskService manualTaskService;

    @Autowired
    private SendLogService sendLogService;

    @Autowired
    private RedisUtils redisUtils;

    public static final String SMS_CHANNEL_FAIL = "sms:channel:fail:";

    /**
     * 发送
     * @param json
     * @return
     */
    public boolean send(String json) {
        Integer level = 1;
        Integer msgErrorNum = 0;

        do {
            log.info("SmsFactory|开始发送短信|{},{}", level, json);
            SendLog sendLog = new SendLog();

            long start = System.currentTimeMillis();
            try{
                SmsSendCommand smsSendCommand = JsonUtil.fromJson(json, SmsSendCommand.class);
                AbstractSmsService abstractSmsService = null;

                if(smsConnectLoader.checkConnectLevel(level)) {
                    log.error("SmsFactory|短信发送失败,需要人工处理");
                    ManualTask manualTask = buildManualTask(smsSendCommand);
                    manualTaskService.save(manualTask);

                    sendLog.setChannelId(0L);
                    sendLog.setChannelName("not found");
                    sendLog.setChannelPlatform("not found");
                    sendLog.setMobile(smsSendCommand.getMobile());
                    sendLog.setSignature(smsSendCommand.getSignature());
                    sendLog.setTemplate(smsSendCommand.getTemplate());
                    sendLog.setRequest(JSON.toJSONString(smsSendCommand));
                    sendLog.setStatus(0);
                    sendLog.setResponse("not found channel");
                    return false;
                }

                AbstractSmsService smsService = smsConnectLoader.getConnectByLevel(level);
                Long channelId = smsService.getChannel().getId();
                if(smsSendCommand.getChannelIdList().contains(channelId)) {
                    abstractSmsService = smsConnectLoader.getConnectByLevel(level);
                    if(abstractSmsService == null) {
                        log.info("SmsFactory|当前级别的通道为空|{},{}", channelId, level);
                        level++;
                        continue;
                    }
                    log.info("SmsFactory|当前级别支持发送|{},{},{}", channelId, abstractSmsService.getClass().getName(), level);
                }else {
                    log.info("SmsFactory|当前级别不支持发送|{},{}", channelId, level);
                    level++;
                    continue;
                }

                //构建日志
                sendLog.setChannelId(abstractSmsService.getChannel().getId());
                sendLog.setChannelName(abstractSmsService.getChannel().getName());
                sendLog.setChannelPlatform(abstractSmsService.getChannel().getPlatform());
                sendLog.setMobile(smsSendCommand.getMobile());
                sendLog.setSignature(smsSendCommand.getSignature());
                sendLog.setTemplate(smsSendCommand.getTemplate());
                sendLog.setRequest(JsonUtil.toJson(smsSendCommand));
                sendLog.setApiLogId(0L);
                sendLog.setStatus(1);

                //发送短信
                String response = abstractSmsService.send(smsSendCommand.getMobile(), smsSendCommand.getParams(), smsSendCommand.getSignature(), smsSendCommand.getTemplate());
                sendLog.checkResponse(response);

                log.info("SmsFactory|短信发送成功|{}", response);
                return true;
            }catch (Exception e) {
                log.error("SmsFactory|短信发送异常|", e);
                sendLog.setStatus(0);
                sendLog.setError(getExceptionMessage(e));

                //如果通道失败次数超过阈值，降级通道，重新排序
                //如果失败次数超过一定比例，则启动新通道备用
                if(resetChannel(level)) {
                    level = 1;
                    continue;
                }

                //如果重试次数超过阈值就切换下一级通道
                if(msgErrorNum >= 3) {
                    msgErrorNum = 0;
                    level++;
                    log.info("SmsFactory|短信通道达到了失败阈值,切换到下一级通道");
                }else {
                    msgErrorNum++;
                }
            }finally {
                //日志存储
                if(Objects.nonNull(sendLog.getChannelId())) {
                    long end = System.currentTimeMillis();
                    sendLog.setTimeConsuming(end - start);
                    sendLogService.save(sendLog);
                }
            }

        }while (true);
    }

    private boolean resetChannel(Integer level) {
        String smsChannelFailNumStr = redisUtils.get(SMS_CHANNEL_FAIL + level);
        if(StringUtils.isBlank(smsChannelFailNumStr)) {
            smsChannelFailNumStr = "0";
        }

        Integer smsChannelFailNum = Integer.parseInt(smsChannelFailNumStr);
        if(smsChannelFailNum >= 6) {
            //通道重新排序
            log.info("SmsFactory|通道失败次数过多，重新排序|");
            smsConnectLoader.changeNewConnectMessage();
            return true;
        }else {
            if(smsChannelFailNum >= 4) {
                //通道重新选举
                smsConnectLoader.buildNewConnect();
            }
            redisUtils.setEx(SMS_CHANNEL_FAIL + level, String.valueOf(smsChannelFailNum + 1), 10, TimeUnit.MINUTES);
        }

        return false;
    }

    private ManualTask buildManualTask(SmsSendCommand smsSendCommand) {
        ManualTask manualTask = new ManualTask();
        manualTask.setId(idWorker.nextId());
        manualTask.setTemplate(smsSendCommand.getTemplate());
        manualTask.setSignature(smsSendCommand.getSignature());
        manualTask.setMobile(smsSendCommand.getMobile());
        manualTask.setRequest(JsonUtil.toJson(smsSendCommand.getParams()));
        manualTask.setChannelIds(StringUtils.join(smsSendCommand.getChannelIdList()));
        return manualTask;
    }

    private String getExceptionMessage(Exception e){
        StringWriter sw = new StringWriter();
        PrintWriter printWriter = new PrintWriter(sw);
        e.printStackTrace(printWriter);
        return sw.toString();
    }
}
