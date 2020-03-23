package com.whoiszxl.search.listener;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: whoiszxl
 * @create: 2020-03-23
 **/

/**
 * 商品
 */
@Component
public class ProductUpListener implements RocketMQListener, RocketMQPushConsumerLifecycleListener {
    @Override
    public void onMessage(Object o) {
        System.out.println("接收消息" + o.toString());
    }

    @Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {

    }
}
