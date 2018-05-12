package com.whoiszxl.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * spring session 配置
 * @author whoiszxl
 *
 */
@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 36000)
public class RedisSessionConfig {

}
