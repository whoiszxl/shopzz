package com.whoiszxl.taowu.im.mq;

/**
 * MQ发送服务接口
 * @author whoiszxl
 */
public interface MqSenderService {

    /**
     * 停机
     */
    void shutdown();

    /**
     * 立即发送消息
     * @param topic 消息topic
     * @param message 消息体
     * @return 是否发送成功
     */
    boolean sendMessage(String topic, String message);

    /**
     * 延迟发送消息
     * @param topic 消息topic
     * @param message 消息体
     * @param delayTimeLevel 延迟发送等级[1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h]
     * @return 是否发送成功
     */
    boolean sendMessage(String topic, String message, Integer delayTimeLevel);

}
