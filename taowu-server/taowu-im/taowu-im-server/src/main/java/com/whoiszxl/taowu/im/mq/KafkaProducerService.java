package com.whoiszxl.taowu.im.mq;

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
        kafkaTemplate.send(topic, message);
        return true;
    }

    @Override
    public boolean sendMessage(String topic, String message, Integer delayTimeLevel) {
        kafkaTemplate.send(topic, message);
        return true;
    }
}
