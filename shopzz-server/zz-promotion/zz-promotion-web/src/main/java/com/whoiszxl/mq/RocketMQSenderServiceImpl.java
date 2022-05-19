package com.whoiszxl.mq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;

/**
 * RocketMQ消息发送服务实现
 *
 * @author whoiszxl
 * @date 2022/5/19
 */
@Slf4j
@Service
public class RocketMQSenderServiceImpl implements MQSenderService {

    @Value("${rocketmq.name-server}")
    private String nameServer;

    @Value("${rocketmq.seckill.producer.group}")
    private String producerGroup;

    private DefaultMQProducer defaultMQProducer;

    @PostConstruct
    public void init() {
        try {
            defaultMQProducer = new DefaultMQProducer(producerGroup);
            defaultMQProducer.setNamesrvAddr(nameServer);
            defaultMQProducer.start();
            log.info("RocketMQSenderServiceImpl.init|秒杀服务生产者初始化成功|{},{}", nameServer, producerGroup);
        }catch (Exception e) {
            log.error("RocketMQSenderServiceImpl.init|秒杀服务生产者初始化失败|{},{}", nameServer, producerGroup, e);
        }
    }


    @Override
    public void shutdown() {
        defaultMQProducer.shutdown();
    }

    @Override
    public boolean sendMessage(String topic, String message) {
        return sendMessage(topic, message, -1);
    }

    @Override
    public boolean sendMessage(String topic, String message, Integer delayTimeLevel) {
        Message msg = new Message(topic, message.getBytes(StandardCharsets.UTF_8));
        try {
            if (delayTimeLevel > 0) {
                msg.setDelayTimeLevel(delayTimeLevel);
            }
            SendResult sendResult = defaultMQProducer.send(msg);
            if (SendStatus.SEND_OK.equals(sendResult.getSendStatus())) {
                log.info("RocketMQSenderServiceImpl.sendMessage|发送MQ消息成功|{}", message);
                return true;
            } else {
                log.info("RocketMQSenderServiceImpl.sendMessage|发送MQ消息失败|{}", message);
                return false;
            }
        } catch (Exception e) {
            log.error("RocketMQSenderServiceImpl.sendMessage|发送MQ消息发生异常|{}", message, e);
            return false;
        }
    }
}
