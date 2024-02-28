package com.whoiszxl.taowu.starter.lock.configuration;


import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * redisson注入
 * 在web环境，taowu.redisson.enabled=true时开启
 * @author whoiszxl
 */
@Configuration
@ConditionalOnWebApplication
@ConditionalOnProperty(name = "taowu.redisson.enabled", havingValue = "true", matchIfMissing = true)
public class RedissonConfiguration {

    @Value("${taowu.redisson.host}")
    private String redissonHost;

    @Value("${taowu.redisson.password}")
    private String redissonPassword;

    @Bean
    @ConditionalOnMissingBean
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.useSingleServer()
                .setAddress(redissonHost)
                .setPassword(redissonPassword)
                .setDatabase(1);
        return Redisson.create(config);
    }
}
