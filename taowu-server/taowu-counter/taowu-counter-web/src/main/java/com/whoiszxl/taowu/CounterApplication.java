package com.whoiszxl.taowu;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.hutool.extra.spring.EnableSpringUtil;
import com.whoiszxl.taowu.common.properties.TaowuProperties;
import com.whoiszxl.taowu.common.utils.IdWorker;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;

/**
 * counter 启动类
 *
 * 写场景
 * 数据获取方式三种： Feign、接口直接调用、MQ获取（业务发送MQ消息或者通过 binlog 监听进行消费）
 *
 * 数据落盘：数据库直接新增或者加入缓存中统一更新
 *
 * 读场景：
 * 直接走多级缓存查询 LocalCache -> DistributedCache -> Database，看情况是否需要判断 HotKey
 *
 * @author whoiszxl
 */
@Slf4j
@RestController
@SpringBootApplication
@RequiredArgsConstructor
@EnableSpringUtil
@EnableScheduling
public class CounterApplication implements ApplicationRunner {

    private final TaowuProperties properties;

    private final ServerProperties serverProperties;

    public static void main(String[] args) {
        SpringApplication.run(CounterApplication.class, args);
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
