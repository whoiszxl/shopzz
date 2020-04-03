package com.whoiszxl.user;

import com.whoiszxl.common.interceptor.FeignInterceptor;
import com.whoiszxl.common.utils.IdWorker;
import com.whoiszxl.common.utils.RedisUtils;
import com.whoiszxl.common.sms.SmsSender;
import com.whoiszxl.user.config.TokenDecode;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @description: 用户服务启动类
 * @author: whoiszxl
 * @create: 2020-03-19
 **/
@SpringBootApplication
@MapperScan("com.whoiszxl.user.mapper")
@ComponentScan(basePackages = {"com.whoiszxl.user", "com.whoiszxl.common.listener", "com.whoiszxl.common.exception"})
@EnableDiscoveryClient
public class UserApplication {

    @Value("${workerId}")
    private Integer workerId;

    @Value("${datacenterId}")
    private Integer datacenterId;

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class,args);
    }

    @Bean
    public IdWorker idWorker(){
        return new IdWorker(workerId,datacenterId);
    }

    @Bean
    public TokenDecode tokenDecode() {
        return new TokenDecode();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RedisUtils redisUtils() {
        return new RedisUtils();
    }

    @Bean
    public SmsSender smsSender() {
        return new SmsSender();
    }

    @Bean
    public FeignInterceptor feignInterceptor() {
        return new FeignInterceptor();
    }
}
