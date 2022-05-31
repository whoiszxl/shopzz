package com.whoiszxl.mq;

import com.whoiszxl.constants.RocketMQConstant;
import com.whoiszxl.factory.SmsFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 营销短信消费者
 *
 * @author whoiszxl
 * @date 2022/5/19
 */
@Slf4j
//@Component
//@RocketMQMessageListener(topic = RocketMQConstant.SMS_PROMOTION_TOPIC, consumerGroup = RocketMQConstant.SMS_GROUP)
public class SmsPromotionConsumer implements RocketMQListener<String> {

    @Autowired
    private SmsFactory smsFactory;

    @Override
    public void onMessage(String json) {
        log.info("SmsPromotionConsumer.onMessage|接收发送营销短信任务|{}", json);
        if(StringUtils.isEmpty(json)) {
            log.info("SmsPromotionConsumer.onMessage|接收发送营销短信任务，任务消息为空|");
            return;
        }

        try{
            smsFactory.send(json);
            log.info("SmsPromotionConsumer.onMessage|接收发送营销短信任务,任务处理成功|{}", json);
        }catch (Exception e) {
            log.error("SmsPromotionConsumer.onMessage|接收发送营销短信任务发生异常|{}", json, e);
        }
    }
}
