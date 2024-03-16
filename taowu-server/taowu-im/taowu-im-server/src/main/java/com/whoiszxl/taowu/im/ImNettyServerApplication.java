package com.whoiszxl.taowu.im;

import cn.hutool.extra.spring.EnableSpringUtil;
import com.whoiszxl.taowu.common.exception.ExceptionCatcher;
import com.whoiszxl.taowu.common.properties.TaowuProperties;
import com.whoiszxl.taowu.im.properties.ImNettyProperties;
import com.whoiszxl.taowu.im.server.NettyServer;
import com.whoiszxl.taowu.im.server.WebSocketServer;
import io.netty.channel.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.*;

/**
 * IM Netty服务应用启动类
 * @author whoiszxl
 */
@Slf4j
@RestController
@EnableSpringUtil
@SpringBootApplication
@RequiredArgsConstructor
@ComponentScan(basePackages = {
        "com.whoiszxl.taowu.**",
})
@EnableFeignClients(basePackages = {
        "com.whoiszxl.taowu.im.feign"
})
public class ImNettyServerApplication extends SpringBootServletInitializer implements CommandLineRunner {

    private final NettyServer nettyServer;

    private final WebSocketServer webSocketServer;

    private final TaowuProperties properties;

    private final ImNettyProperties imNettyProperties;

    public static void main(String[] args) {
        SpringApplication.run(ImNettyServerApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(ImNettyServerApplication.class);
    }

    @Override
    public void run(String... args) throws Exception {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                2,
                5,
                3,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy()
        );

        Future<Channel> future = threadPoolExecutor.submit(nettyServer);
        Channel channel = future.get();
        if(channel == null) {
            ExceptionCatcher.catchServiceEx("ImNettyServerApplication|IM Netty服务启动失败");
        }

        while(!channel.isActive()) {
            log.info("IM NettyServer启动中");
            Thread.sleep(1000);
        }

        Future<Channel> webSocketFuture = threadPoolExecutor.submit(webSocketServer);
        Channel webSocketChannel = webSocketFuture.get();
        if(webSocketChannel == null) {
            ExceptionCatcher.catchServiceEx("ImNettyServerApplication|IM Netty WebSocket服务启动失败");
        }

        while(!webSocketChannel.isActive()) {
            log.info("IM NettyServer启动中");
            Thread.sleep(1000);
        }

        log.info("{} 服务启动成功", properties.getProjectName());
        log.info("WebSocket服务端口:{}, TCP服务端口:{}", imNettyProperties.getWebsocketPort(), imNettyProperties.getTcpPort());
    }
}
