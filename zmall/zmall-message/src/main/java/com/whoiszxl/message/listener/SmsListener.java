package com.whoiszxl.message.listener;

/**
 * @description:
 * @author: whoiszxl
 * @create: 2020-04-02
 **/

import com.aliyun.mq.http.MQClient;
import com.aliyun.mq.http.MQConsumer;
import com.aliyun.mq.http.common.AckMessageException;
import com.aliyun.mq.http.model.Message;
import com.whoiszxl.common.config.MqTopicEnums;
import com.whoiszxl.common.config.RocketMQConfig;
import com.whoiszxl.common.entity.Result;
import com.whoiszxl.message.provider.SMSProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品
 */
@Component
@Slf4j
public class SmsListener {

    @Value("${aliyun.accountEndpoint}")
    private String accountEndpoint;

    @Value("${aliyun.accessId}")
    private String accessId;

    @Value("${aliyun.accessKey}")
    private String accessKey;

    /** Topic所属实例ID，默认实例为空 */
    @Value("${rocketmq.instanceId}")
    private String instanceId;

    @Value("${rocketmq.groupId}")
    private String groupId;

    @Autowired
    private SMSProvider aliyunSMSProvider;


    /** 所属的 Topic */
    private String topic = MqTopicEnums.MESSAGE_SMS_TOPIC.getName();

    @Scheduled(initialDelay = 1000, fixedDelay = 5000)
    public void execute() {
        MQClient mqClient = new MQClient(
                // 设置HTTP接入域名（此处以公共云生产环境为例）
                accountEndpoint,
                // AccessKey 阿里云身份验证，在阿里云服务器管理控制台创建
                accessId,
                // SecretKey 阿里云身份验证，在阿里云服务器管理控制台创建
                accessKey
        );

        final MQConsumer consumer;
        if (instanceId != null && instanceId != "") {
            consumer = mqClient.getConsumer(instanceId, topic, groupId, null);
        } else {
            consumer = mqClient.getConsumer(topic, groupId);
        }

        // 在当前线程循环消费消息，建议是多开个几个线程并发消费消息
        do {
            List<Message> messages = null;

            try {
                // 长轮询消费消息
                // 长轮询表示如果topic没有消息则请求会在服务端挂住3s，3s内如果有消息可以消费则立即返回
                messages = consumer.consumeMessage(
                        10,// 一次最多消费3条(最多可设置为16条)
                        3// 长轮询时间3秒（最多可设置为30秒）
                );
            } catch (Throwable e) {
                log.error("获取消息失败", e);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
            // 没有消息
            if (messages == null || messages.isEmpty()) {
                log.info(Thread.currentThread().getName() + ": no new message, continue!");
                continue;
            }

            // 处理业务逻辑
            for (Message message : messages) {
                String[] split = message.getMessageBodyString().split(":::::");
                String phoneNumber = split[0];
                String verifyCode = split[1];

                Result result = aliyunSMSProvider.sendVerifyMessage(phoneNumber, verifyCode);
                log.info("发送验证码短信到：{}，验证码为：{}，回馈记录为：{}", phoneNumber, verifyCode, result.getResult());
            }

            // Message.nextConsumeTime前若不确认消息消费成功，则消息会重复消费
            // 消息句柄有时间戳，同一条消息每次消费拿到的都不一样
            {
                List<String> handles = new ArrayList<String>();
                for (Message message : messages) {
                    handles.add(message.getReceiptHandle());
                }

                try {
                    consumer.ackMessage(handles);
                } catch (Throwable e) {
                    // 某些消息的句柄可能超时了会导致确认不成功
                    if (e instanceof AckMessageException) {
                        AckMessageException errors = (AckMessageException) e;
                        System.out.println("Ack message fail, requestId is:" + errors.getRequestId() + ", fail handles:");
                        if (errors.getErrorMessages() != null) {
                            for (String errorHandle :errors.getErrorMessages().keySet()) {
                                System.out.println("Handle:" + errorHandle + ", ErrorCode:" + errors.getErrorMessages().get(errorHandle).getErrorCode()
                                        + ", ErrorMsg:" + errors.getErrorMessages().get(errorHandle).getErrorMessage());
                            }
                        }
                        continue;
                    }
                    e.printStackTrace();
                }
            }
        } while (true);
    }
}
