package com.whoiszxl.product.listener;//package com.whoiszxl.product.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class KafkaSender implements MQSender {


    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void send(String message, String tag) {
        kafkaTemplate.send("hello", message);
    }
}
