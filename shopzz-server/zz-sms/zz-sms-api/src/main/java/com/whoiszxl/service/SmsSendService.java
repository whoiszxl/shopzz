package com.whoiszxl.service;

import com.whoiszxl.cqrs.command.SmsSendCommand;

/**
 * 短信发送服务接口
 *
 * @author whoiszxl
 * @date 2022/5/26
 */
public interface SmsSendService {

    /**
     * 单条短信发送
     * @param smsSendCommand
     */
    void send(SmsSendCommand smsSendCommand);
}
