package com.whoiszxl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 普通支付服务启动类
 *
 * @author whoiszxl
 * @date 2021/7/19
 */
@SpringBootApplication
@EnableFeignClients
public class PayApplication {

    public static void main(String[] args) {
        SpringApplication.run(PayApplication.class, args);
    }
}
