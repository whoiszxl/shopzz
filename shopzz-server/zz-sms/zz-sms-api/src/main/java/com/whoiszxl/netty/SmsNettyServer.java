package com.whoiszxl.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * SMS netty服务
 *
 * @author whoiszxl
 * @date 2022/5/27
 */
@Slf4j
@Component
public class SmsNettyServer implements CommandLineRunner {
    
    private static SmsNettyServer smsNettyServer;
    
    @PostConstruct
    public void init() {
        smsNettyServer = this;
    }
    
    private int port = 20000;
    
    private EventLoopGroup parentGroup;
    private EventLoopGroup childGroup;
    private ServerBootstrap serverBootstrap;
    private ChannelFuture channelFuture;

    private void start() {
        parentGroup = new NioEventLoopGroup(2);
        childGroup = new NioEventLoopGroup(4);

        try{
            serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(parentGroup, childGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 60000)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new SmsNettyServerInitializer());

            channelFuture = serverBootstrap.bind(port).sync();
            log.info("SmsNettyServer|sms netty服务启动|{}", port);

            channelFuture.channel().closeFuture().sync();
        }catch (Exception e) {
            log.error("SmsNettyServer|sms netty服务启动异常|", e);
        }finally {
            parentGroup.shutdownGracefully();
            childGroup.shutdownGracefully();
        }
    }

    @Override
    public void run(String... args) throws Exception {
        new Thread("smsNetty") {
            @Override
            public void run() {
                smsNettyServer.start();
            }
        }.start();
    }
}
