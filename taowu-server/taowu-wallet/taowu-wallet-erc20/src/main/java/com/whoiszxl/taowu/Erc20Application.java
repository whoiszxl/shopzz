package com.whoiszxl.taowu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 普通支付服务启动类
 *
 * @author whoiszxl
 * @date 2021/7/19
 */
@EnableScheduling
@SpringBootApplication
@EnableFeignClients
public class Erc20Application {

    public static void main(String[] args) {
        SpringApplication.run(Erc20Application.class, args);
    }
}
