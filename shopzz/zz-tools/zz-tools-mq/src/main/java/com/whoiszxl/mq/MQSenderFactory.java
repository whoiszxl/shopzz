package com.whoiszxl.mq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * MQ发送者创建工厂
 */
@Component
public class MQSenderFactory {

    @Autowired
    private KafkaMQSender kafkaMQSender;

    @Autowired
    private RocketMQSender rocketMQSender;

    /**
     * 通过枚举类型获取对应的消息发送者
     * @param mqEnum 消息队列枚举类型
     * @return 消息队列发送者
     */
    public MQSender get(MQEnum mqEnum) {
        if(mqEnum.equals(MQEnum.KAFKA)) {
            return kafkaMQSender;
        }

        if(mqEnum.equals(MQEnum.ROCKETMQ)) {
            return rocketMQSender;
        }

        return kafkaMQSender;
    }
}
