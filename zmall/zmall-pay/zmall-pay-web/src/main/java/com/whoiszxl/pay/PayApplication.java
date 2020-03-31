package com.whoiszxl.pay;

import com.whoiszxl.common.utils.IdWorker;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * @description: 支付服务启动类
 * @author: whoiszxl
 * @create: 2020-03-19
 **/
@SpringBootApplication
@MapperScan("com.whoiszxl.pay.mapper")
@ComponentScan(basePackages = {"com.whoiszxl.pay"})
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.whoiszxl.product.feign", "com.whoiszxl.user.feign"})
public class PayApplication {

    @Value("${workerId}")
    private Integer workerId;

    @Value("${datacenterId}")
    private Integer datacenterId;

    public static void main(String[] args) {
        SpringApplication.run(PayApplication.class, args);
    }

    @Bean
    public IdWorker idWorker() {
        return new IdWorker(workerId, datacenterId);
    }
}
