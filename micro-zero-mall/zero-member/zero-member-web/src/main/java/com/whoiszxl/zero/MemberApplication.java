package com.whoiszxl.zero;

import com.whoiszxl.zero.utils.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

/**
 * 会员应用启动器
 *
 * @author whoiszxl
 * @date 2021/4/7
 */
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class MemberApplication {

    public static void main(String[] args) {
        SpringApplication.run(MemberApplication.class, args);
    }

    @Bean
    public IdWorker idWorker() {
        return new IdWorker();
    }
}
