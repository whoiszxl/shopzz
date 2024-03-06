package com.whoiszxl.taowu.consumer;

import com.whoiszxl.taowu.common.constants.RocketMQConstant;
import com.whoiszxl.taowu.common.utils.JsonUtil;
import com.whoiszxl.taowu.common.utils.LogFormatUtil;
import com.whoiszxl.taowu.cqrs.dto.SeckillPlaceOrderDTO;
import com.whoiszxl.taowu.service.PlaceOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * 秒杀下单消费者
 *
 * @author whoiszxl
 * @date 2022/5/19
 */
@Slf4j
@RequiredArgsConstructor
@Component
@RocketMQMessageListener(topic = RocketMQConstant.SECKILL_PLACE_ORDER_TOPIC, consumerGroup = RocketMQConstant.SECKILL_GROUP)
public class SeckillPlaceOrderConsumer implements RocketMQListener<String> {

    private final PlaceOrderService placeOrderService;

    @Override
    public void onMessage(String json) {
        log.info(LogFormatUtil.format(this, "接收秒杀下单任务消息", json));
        if(StringUtils.isEmpty(json)) {
            log.info(LogFormatUtil.format(this, "接收秒杀下单任务消息，消息为空", null));
            return;
        }

        try{
            SeckillPlaceOrderDTO seckillPlaceOrderDTO = JsonUtil.fromJson(json, SeckillPlaceOrderDTO.class);
            placeOrderService.handlePlaceOrderTask(seckillPlaceOrderDTO);
            log.info(LogFormatUtil.format(this, "接收秒杀下单任务消息,任务处理成功", json));
        }catch (Exception e) {
            log.error(LogFormatUtil.format(this, "接收秒杀下单任务消息发生异常", json), e);
        }
    }
}
