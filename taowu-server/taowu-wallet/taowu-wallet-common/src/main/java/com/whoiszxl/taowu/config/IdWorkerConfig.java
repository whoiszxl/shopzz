package com.whoiszxl.taowu.config;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class IdWorkerConfig {

    @Bean
    public IdWorker idWorker() {
        return new IdWorker();
    }
}
