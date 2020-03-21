package com.whoiszxl.system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @description: 商品服务启动类
 * @author: whoiszxl
 * @create: 2020-03-19
 **/
@SpringBootApplication
@MapperScan("com.whoiszxl.system.mapper")
@ComponentScan(basePackages = {"com.whoiszxl.system"})
@EnableDiscoveryClient
public class SystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class,args);
    }
}
