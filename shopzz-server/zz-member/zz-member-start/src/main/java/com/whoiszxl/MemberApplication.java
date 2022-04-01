package com.whoiszxl;

import com.whoiszxl.utils.IdWorker;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

/**
 * 会员服务启动类
 *
 * @author whoiszxl
 * @date 2021/7/19
 */
@SpringBootApplication
@EnableFeignClients
@MapperScan("com.whoiszxl.db.mapper")
public class MemberApplication {

    public static void main(String[] args) {
        SpringApplication.run(MemberApplication.class, args);
    }

    @Bean
    public IdWorker idWorker() {
        return new IdWorker();
    }
}
