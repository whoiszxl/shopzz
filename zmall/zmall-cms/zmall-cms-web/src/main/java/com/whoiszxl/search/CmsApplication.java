package com.whoiszxl.search;

import com.whoiszxl.common.utils.IdWorker;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * @description: 商品服务启动类
 * @author: whoiszxl
 * @create: 2020-03-19
 **/
@ComponentScan(basePackages = {"com.whoiszxl.search", "com.whoiszxl.common.exception"})
@SpringBootApplication
@MapperScan("com.whoiszxl.cms.mapper")
@EnableDiscoveryClient
public class CmsApplication {

    @Value("${workerId}")
    private Integer workerId;

    @Value("${datacenterId}")
    private Integer datacenterId;

    public static void main(String[] args) {
        SpringApplication.run(CmsApplication.class,args);
    }

    @Bean
    public IdWorker idWorker(){
        return new IdWorker(workerId,datacenterId);
    }
}
