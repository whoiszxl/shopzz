package com.whoiszxl.order.listener;

import com.aliyun.mq.http.MQClient;
import com.aliyun.mq.http.MQConsumer;
import com.aliyun.mq.http.common.AckMessageException;
import com.aliyun.mq.http.model.Message;
import com.whoiszxl.common.config.MqTopicEnums;
import com.whoiszxl.common.config.RocketMQConfig;
import com.whoiszxl.common.listener.RocketMQSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 订单支付成功消息
 * @author: whoiszxl
 * @create: 2020-04-01
 **/
@Component
@Slf4j
public class OrderPayListener {

    @Value("${aliyun.accountEndpoint}")
    private String accountEndpoint;

    @Value("${aliyun.accessId}")
    private String accessId;

    @Value("${aliyun.accessKey}")
    private String accessKey;

    /** Topic所属实例ID，默认实例为空 */
    @Value("${rocketmq.instanceId}")
    private String instanceId;

    /** 所属的 Topic */
    @Value("${rocketmq.groupId}")
    private String groupId;


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
            consumer = mqClient.getConsumer(instanceId, MqTopicEnums.PAY_RESULT_TOPIC.getName(), groupId, null);
        } else {
            consumer = mqClient.getConsumer(MqTopicEnums.PAY_RESULT_TOPIC.getName(), groupId);
        }

        // 在当前线程循环消费消息，建议是多开个几个线程并发消费消息
        do {
            List<Message> messages = null;

            try {
                // 长轮询消费消息
                // 长轮询表示如果topic没有消息则请求会在服务端挂住3s，3s内如果有消息可以消费则立即返回
                messages = consumer.consumeMessage(
                        3,// 一次最多消费3条(最多可设置为16条)
                        3// 长轮询时间3秒（最多可设置为30秒）
                );
            } catch (Throwable e) {
                e.printStackTrace();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
            // 没有消息
            if (messages == null || messages.isEmpty()) {
                System.out.println(Thread.currentThread().getName() + ": no new message, continue!");
                continue;
            }

            // 处理业务逻辑
            for (Message message : messages) {
                String messageTag = message.getMessageTag();
                if(messageTag.equals(RocketMQConfig.PAY_QUERY)) {
                    String groupMessage = message.getMessageBodyString();
                    String[] split = groupMessage.split(":::::");
                    String mallOrderId = split[0];
                    String alipayOrderId = split[1];
                    log.info("接收到了订单支付成功的消息，商城订单ID是：{}, 支付宝订单ID是：{}", mallOrderId, alipayOrderId);

                    //调用order业务，修改数据库
                }
                System.out.println("Receive message: " + message);
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
                                System.out.println("Handles:" + errorHandle + ", ErrorCode:" + errors.getErrorMessages().get(errorHandle).getErrorCode()
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
