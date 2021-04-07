package com.whoiszxl.promotion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 促销应用启动器
 *
 * @author whoiszxl
 * @date 2021/4/7
 */
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class PromotionApplication {

    public static void main(String[] args) {
        SpringApplication.run(PromotionApplication.class, args);
    }
}
