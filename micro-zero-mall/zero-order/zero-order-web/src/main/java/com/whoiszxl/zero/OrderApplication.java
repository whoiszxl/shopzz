package com.whoiszxl.zero;

import com.whoiszxl.zero.utils.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 订单应用启动器
 *
 * @author whoiszxl
 * @date 2021/4/7
 */
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }

    @Bean
    public IdWorker idWorker() {
        return new IdWorker();
    }

    @Bean
    public ThreadPoolExecutor threadPoolExecutor() {
        return new ThreadPoolExecutor(
                5,
                10,
                300,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(10000),
                new ThreadPoolExecutor.AbortPolicy()
        );
    }
}
