package com.whoiszxl.zero.config;

import com.whoiszxl.zero.utils.IdWorker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IdWorkerConfig {

    @Bean
    public IdWorker idWorker() {
        return new IdWorker();
    }
}
