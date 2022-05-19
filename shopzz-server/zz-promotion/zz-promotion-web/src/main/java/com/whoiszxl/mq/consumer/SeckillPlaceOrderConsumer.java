package com.whoiszxl.mq.consumer;

import com.whoiszxl.constants.RocketMQConstant;
import com.whoiszxl.cqrs.dto.SeckillPlaceOrderDTO;
import com.whoiszxl.service.PlaceOrderService;
import com.whoiszxl.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 秒杀下单消费者
 *
 * @author whoiszxl
 * @date 2022/5/19
 */
@Slf4j
@Component
@RocketMQMessageListener(topic = RocketMQConstant.SECKILL_PLACE_ORDER_TOPIC, consumerGroup = RocketMQConstant.SECKILL_GROUP)
public class SeckillPlaceOrderConsumer implements RocketMQListener<String> {

    @Autowired
    private PlaceOrderService placeOrderService;

    @Override
    public void onMessage(String json) {
        log.info("SeckillPlaceOrderConsumer.onMessage|接收秒杀下单任务消息|{}", json);
        if(StringUtils.isEmpty(json)) {
            log.info("SeckillPlaceOrderConsumer.onMessage|接收秒杀下单任务消息，消息为空|");
            return;
        }

        try{
            SeckillPlaceOrderDTO seckillPlaceOrderDTO = JsonUtil.fromJson(json, SeckillPlaceOrderDTO.class);
            placeOrderService.handlePlaceOrderTask(seckillPlaceOrderDTO);
            log.info("SeckillPlaceOrderConsumer.onMessage|接收秒杀下单任务消息,任务处理成功|{}", json);
        }catch (Exception e) {
            log.error("SeckillPlaceOrderConsumer.onMessage|接收秒杀下单任务消息发生异常|{}", json, e);
        }
    }
}
