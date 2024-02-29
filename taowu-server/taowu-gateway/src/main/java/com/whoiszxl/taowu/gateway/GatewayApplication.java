package com.whoiszxl.taowu.gateway;

import cn.hutool.extra.spring.EnableSpringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;

/**
 * @author whoiszxl
 */
@Slf4j
@RestController
@SpringBootApplication
@RequiredArgsConstructor
@EnableSpringUtil
public class GatewayApplication implements ApplicationRunner {

    private final ServerProperties serverProperties;

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String hostAddress = InetAddress.getLocalHost().getHostAddress();
        log.info("淘物网关服务启动成功");
        log.info("服务地址: http://{}:{}", hostAddress, serverProperties.getPort());
        log.info("文档地址: http://{}:{}/doc.html", hostAddress, serverProperties.getPort());
    }
}
