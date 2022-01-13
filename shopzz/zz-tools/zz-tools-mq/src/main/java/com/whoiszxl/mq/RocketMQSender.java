package com.whoiszxl.mq;

import org.springframework.stereotype.Component;

@Component
public class RocketMQSender implements MQSender{

    @Override
    public void send(String topic, String message) {
        System.out.println("send " + message + " to " + topic);
    }
}
