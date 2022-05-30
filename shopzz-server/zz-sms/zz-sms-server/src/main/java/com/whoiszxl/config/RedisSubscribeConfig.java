package com.whoiszxl.config;

import com.whoiszxl.constants.RedisKeyPrefixConstants;
import com.whoiszxl.register.SmsServerReceiver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

/**
 * Redis订阅模式配置
 *
 * @author whoiszxl
 * @date 2022/5/30
 */
@Configuration
@AutoConfigureAfter({SmsServerReceiver.class})
public class RedisSubscribeConfig {

    @Autowired
    private SmsServerReceiver smsServerReceiver;

    @Bean
    public RedisMessageListenerContainer getRedisMessageListenerContainer(RedisConnectionFactory redisConnectionFactory) {
        RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
        redisMessageListenerContainer.setConnectionFactory(redisConnectionFactory);

        redisMessageListenerContainer.addMessageListener(new MessageListenerAdapter(smsServerReceiver), new PatternTopic(RedisKeyPrefixConstants.SMS_TOPIC_SERVER));
        return redisMessageListenerContainer;
    }

}
