package com.whoiszxl.erp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * ERP物流管理应用启动器
 *
 * @author whoiszxl
 * @date 2021/4/7
 */
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class ErpApplication {

    public static void main(String[] args) {
        SpringApplication.run(ErpApplication.class, args);
    }
}
