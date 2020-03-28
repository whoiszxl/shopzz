package com.whoiszxl.order;

import com.whoiszxl.common.utils.IdWorker;
import com.whoiszxl.order.config.TokenDecode;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * @description: 订单服务启动类
 * @author: whoiszxl
 * @create: 2020-03-19
 **/
@SpringBootApplication
@MapperScan("com.whoiszxl.order.mapper")
@ComponentScan(basePackages = {"com.whoiszxl.order"})
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.whoiszxl.product.feign", "com.whoiszxl.user.feign"})
public class OrderApplication {

    @Value("${workerId}")
    private Integer workerId;

    @Value("${datacenterId}")
    private Integer datacenterId;

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class,args);
    }

    @Bean
    public IdWorker idWorker(){
        return new IdWorker(workerId,datacenterId);
    }

    @Bean
    public TokenDecode tokenDecode() {
        return new TokenDecode();
    }
}
