package com.whoiszxl.common.listener;

public interface MQSender {

    /**
     * 同步发送消息
     * @param topic 消息主题
     * @param message 消息体
     * @param tag tag标记
     */
    void send(String topic, String message, String tag);

    /**
     * 同步发送消息
     * @param topic 消息主题
     * @param message 消息体
     */
    void send(String topic, String message);

    /**
     * 异步延迟发送
     * @param topic 消息主题
     * @param message 消息体
     * @param tag tag标记
     * @param second 延迟多少秒
     */
    void asyncSend(String topic, String message, String tag, Integer second);

    /**
     * 异步延迟发送
     * @param topic 消息主题
     * @param message 消息体
     * @param second 延迟多少秒
     */
    void asyncSend(String topic, String message, Integer second);
}
