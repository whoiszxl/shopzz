package com.whoiszxl.mq;

import com.whoiszxl.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * RocketMQ消息发送服务实现
 *
 * @author whoiszxl
 * @date 2022/5/19
 */
@Slf4j
@Service
@ConditionalOnProperty(name = "shopzz.sms.mq", havingValue = "redis")
public class RedisSenderServiceImpl implements MQSenderService {

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public void shutdown() {

    }

    @Override
    public boolean sendMessage(String topic, String message) {
        redisUtils.lLeftPush(topic, message);
        return true;
    }

    @Override
    public boolean sendMessage(String topic, String message, Integer delayTimeLevel) {

        return true;
    }
}
