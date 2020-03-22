package com.whoiszxl.product.listener;

import com.aliyun.mq.http.MQClient;
import com.aliyun.mq.http.MQProducer;
import com.aliyun.mq.http.model.TopicMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class RocketMQSender implements MQSender {


    @Value("${aliyun.accountEndpoint}")
    private String accountEndpoint;

    @Value("${aliyun.accessId}")
    private String accessId;

    @Value("${aliyun.accessKey}")
    private String accessKey;

    /** 所属的 Topic */
    @Value("${rocketmq.topic}")
    private String topic;

    /** Topic所属实例ID，默认实例为空 */
    @Value("${rocketmq.instanceId}")
    private String instanceId;


    @Override
    public void send(String message, String tag) {
        MQClient mqClient = new MQClient(
                // 设置HTTP接入域名（此处以公共云生产环境为例）
                accountEndpoint,
                // AccessKey 阿里云身份验证，在阿里云服务器管理控制台创建
                accessId,
                // SecretKey 阿里云身份验证，在阿里云服务器管理控制台创建
                accessKey
        );

        // 获取Topic的生产者
        MQProducer producer = mqClient.getProducer(instanceId, topic);

        try {
            // 普通消息
            TopicMessage pubMsg = new TopicMessage(message.getBytes(), tag);
            // 设置KEY
            pubMsg.setMessageKey("MessageKey");
            // 同步发送消息，只要不抛异常就是成功
            TopicMessage pubResultMsg = producer.publishMessage(pubMsg);

            // 同步发送消息，只要不抛异常就是成功
            log.info(new Date() + " Send mq message success. Topic is:" + topic + ", msgId is: " + pubResultMsg.getMessageId()
                    + ", bodyMD5 is: " + pubResultMsg.getMessageBodyMD5());
        } catch (Throwable e) {
            // 消息发送失败，需要进行重试处理，可重新发送这条消息或持久化这条数据进行补偿处理
            log.error(new Date() + " Send mq message failed. Topic is:" + topic, e);
        }

        mqClient.close();
    }
}
