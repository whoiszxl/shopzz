package com.whoiszxl.consumer;

import com.whoiszxl.dto.OrderCreateInfoDTO;
import com.whoiszxl.dto.OrderItemDTO;
import com.whoiszxl.mq.MQConstants;
import com.whoiszxl.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 订单提交的消费者
 *
 * @author whoiszxl
 * @date 2021/8/11
 */
@Slf4j
@Component
public class SubmitOrderConsumer {




    @KafkaListener(topics = MQConstants.SUBMIT_ORDER_QUEUE, groupId = "default-group")
    public void submitOrderSub(ConsumerRecord<String, String> record,
                               @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                               Consumer consumer) {
        log.info("订阅到新的提交订单，开始处理, 主题为：{}， 消息值为：{}", topic, record.value());
        OrderCreateInfoDTO orderCreateInfoDTO = JsonUtil.fromJson(record.value(), OrderCreateInfoDTO.class);

        List<OrderItemDTO> orderItemList = orderCreateInfoDTO.getOrderItemList();

        for (OrderItemDTO orderItem : orderItemList) {
            //7.1 通过商品的SKU ID查询到货位库存的明细条目，并进行遍历，一个SKU可能在多个货位上


            //7.2 创建出需要拣货的条目和发货的条目并进行批量入库
            //7.3 更新调度中心的库存
            //7.4 更新wms中心的库存
        }



    }
}
