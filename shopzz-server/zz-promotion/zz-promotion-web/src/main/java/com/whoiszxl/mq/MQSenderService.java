package com.whoiszxl.mq;

/**
 * MQ发送服务接口
 *
 * @author whoiszxl
 * @date 2022/5/19
 */
public interface MQSenderService {

    public void shutdown();

    public boolean sendMessage(String topic, String message);

    public boolean sendMessage(String topic, String message, Integer delayTimeLevel);

}
