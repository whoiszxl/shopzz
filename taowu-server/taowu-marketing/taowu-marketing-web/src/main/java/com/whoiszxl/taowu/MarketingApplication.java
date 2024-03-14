package com.whoiszxl.taowu;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.hutool.extra.spring.EnableSpringUtil;
import com.whoiszxl.taowu.common.properties.TaowuProperties;
import com.whoiszxl.taowu.common.utils.IdWorker;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;

/**
 * spms启动类
 * @author whoiszxl
 */
@Slf4j
@RestController
@SpringBootApplication
@EnableFeignClients
@MapperScan("com.whoiszxl.taowu.mapper")
@EnableSpringUtil
@EnableScheduling
@RequiredArgsConstructor
public class MarketingApplication implements ApplicationRunner {

    private final TaowuProperties properties;

    private final ServerProperties serverProperties;

    public static void main(String[] args) {
        SpringApplication.run(MarketingApplication.class, args);
    }

    @Hidden
    @SaIgnore
    @GetMapping("/")
    public String index() {
        return String.format("%s 服务启动成功", properties.getProjectName());
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String hostAddress = InetAddress.getLocalHost().getHostAddress();
        log.info("*************************************************************");
        log.info("{} 服务启动成功", properties.getProjectName());
        log.info("服务地址: http://{}:{}", hostAddress, serverProperties.getPort());
        log.info("文档地址: http://{}:{}/doc.html", hostAddress, serverProperties.getPort());
        log.info("*************************************************************");
    }

    @Bean
    public IdWorker idWorker() {
        return new IdWorker();
    }


}
