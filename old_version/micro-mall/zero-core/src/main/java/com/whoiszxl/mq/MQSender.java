package com.whoiszxl.mq;

/**
 * MQ发送者接口
 */
public interface MQSender {

    /**
     * 发送消息
     * @param topic 消息主题
     * @param message 消息内容
     */
    void send(String topic, String message);
}
