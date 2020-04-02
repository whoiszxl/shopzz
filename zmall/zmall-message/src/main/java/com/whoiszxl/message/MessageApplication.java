package com.whoiszxl.message;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @description: 信息发送启动类
 * @author: whoiszxl
 * @create: 2020-04-02
 **/
@SpringBootApplication
@ComponentScan(basePackages = {"com.whoiszxl.message"})
@EnableDiscoveryClient
@EnableScheduling
public class MessageApplication {

    public static void main(String[] args) {
        SpringApplication.run(MessageApplication.class,args);
    }

}