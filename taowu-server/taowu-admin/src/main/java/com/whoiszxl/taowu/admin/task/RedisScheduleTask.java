package com.whoiszxl.taowu.admin.task;

import cn.hutool.core.date.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * lettuce在长时间无请求时会连接超时，通过心跳机制解决
 * @author whoiszxl
 */
@Slf4j
@RequiredArgsConstructor
@Configuration
public class RedisScheduleTask {

    private final StringRedisTemplate stringRedisTemplate;

    @Scheduled(fixedRate = 5 * 1000)
    private void configureTasks() {
        // log.info("redis ping!");
        stringRedisTemplate.opsForValue().set("ping", DateUtil.now());
    }
}
