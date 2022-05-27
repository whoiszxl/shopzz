package com.whoiszxl.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * RocketMQ消息发送服务实现
 *
 * @author whoiszxl
 * @date 2022/5/19
 */
@Slf4j
//@Service
public class RocketMQSenderServiceImpl implements MQSenderService {

    @PostConstruct
    public void init() {

    }


    @Override
    public void shutdown() {

    }

    @Override
    public boolean sendMessage(String topic, String message) {
        return sendMessage(topic, message, -1);
    }

    @Override
    public boolean sendMessage(String topic, String message, Integer delayTimeLevel) {
        return true;
    }
}
