package com.whoiszxl.taowu.im.mq.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
@RequiredArgsConstructor
public class KafkaProducerService implements MqSenderService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void shutdown() {

    }

    public boolean sendMessage(String topic, String message) {
        try{
            kafkaTemplate.send(topic, message);
            return true;
        }catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean sendMessage(String topic, String message, Integer delayTimeLevel) {
        kafkaTemplate.send(topic, message);
        return true;
    }
}
