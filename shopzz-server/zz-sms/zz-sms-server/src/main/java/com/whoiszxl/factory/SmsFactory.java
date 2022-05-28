package com.whoiszxl.factory;

import com.whoiszxl.cqrs.command.SmsSendCommand;
import com.whoiszxl.entity.SendLog;
import com.whoiszxl.sms.AbstractSmsService;
import com.whoiszxl.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
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



            }catch (Exception e) {

            }

        }while (true);
    }
}
