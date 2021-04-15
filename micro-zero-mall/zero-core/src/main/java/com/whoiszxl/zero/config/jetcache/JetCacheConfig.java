package com.whoiszxl.zero.config.jetcache;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.springframework.context.annotation.Configuration;

/**
 * jetcache配置
 *
 * @author whoiszxl
 * @date 2021/4/8
 */
@Configuration
@EnableCreateCacheAnnotation
@EnableMethodCache(basePackages = "com.whoiszxl.zero")
public class JetCacheConfig {
}
