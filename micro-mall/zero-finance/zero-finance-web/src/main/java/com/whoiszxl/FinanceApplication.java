package com.whoiszxl;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 财务服务启动类
 *
 * @author whoiszxl
 * @date 2021/7/19
 */
@MapperScan("com.whoiszxl.mapper")
@SpringBootApplication
@EnableFeignClients
public class FinanceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinanceApplication.class, args);
    }
}
