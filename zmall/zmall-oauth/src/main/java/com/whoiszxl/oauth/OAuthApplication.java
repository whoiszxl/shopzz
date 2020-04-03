package com.whoiszxl.oauth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

/**
 * @description: oauth启动器
 * @author: whoiszxl
 * @create: 2020-03-25
 **/
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.whoiszxl.oauth.mapper")
@ComponentScan(basePackages = {"com.whoiszxl.oauth", "com.whoiszxl.common.exception"})
@EnableFeignClients(basePackages = {"com.whoiszxl.user.feign"})
public class OAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(OAuthApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
