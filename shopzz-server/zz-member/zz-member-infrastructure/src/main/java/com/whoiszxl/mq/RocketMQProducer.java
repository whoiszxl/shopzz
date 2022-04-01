package com.whoiszxl.mq;

import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.exception.custom.ValidateException;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.spring.autoconfigure.RocketMQProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
@Slf4j
public class RocketMQProducer {

    private DefaultMQProducer producer;

    @Autowired
    public RocketMQProducer(RocketMQProperties rocketMQProperties) {
//        producer = new DefaultMQProducer(RocketMQConstant.MEMBER_DEFAULT_PRODUCER_GROUP);
//        producer.setNamesrvAddr(rocketMQProperties.getNameServer());
//        start();
    }

    public DefaultMQProducer getProducer() {
        return this.producer;
    }

    /**
     * 对象在使用之前必须要调用一次，只能初始化一次
     */
    public void start() {
        try {
            this.producer.start();
        } catch (MQClientException e) {
            log.error("producer start error", e);
        }
    }


    /**
     * 一般在应用上下文，使用上下文监听器，进行关闭
     */
    public void shutdown() {
        this.producer.shutdown();
    }

    /**
     * 发送消息
     *
     * @param topic   topic
     * @param message 消息
     */
    public void sendMessage(String topic, String message, String type) {
        sendMessage(topic, message, -1, type);
    }

    /**
     * 发送消息
     *
     * @param topic   topic
     * @param message 消息
     */
    public void sendMessage(String topic, String message, Integer delayTimeLevel, String type) {
        Message msg = new Message(topic, message.getBytes(StandardCharsets.UTF_8));
        try {
            if (delayTimeLevel > 0) {
                msg.setDelayTimeLevel(delayTimeLevel);
            }
            SendResult send = producer.send(msg);
            if (SendStatus.SEND_OK == send.getSendStatus()) {
                log.info("发送MQ消息成功, type:{}, message:{}", type, message);
            } else {
                throw new ValidateException(ResponseResult.buildError(send.getSendStatus().toString()));
            }
        } catch (Exception e) {
            log.error("发送MQ消息失败：", e);
            throw new ValidateException(ResponseResult.buildError("发送MQ消息失败"));
        }
    }

}
