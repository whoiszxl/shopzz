package com.whoiszxl.taowu.im.server;

import com.whoiszxl.taowu.common.utils.RedisUtils;
import com.whoiszxl.taowu.im.codec.MessageDecoder;
import com.whoiszxl.taowu.im.codec.MessageEncoder;
import com.whoiszxl.taowu.im.feign.PermissionCheckFeign;
import com.whoiszxl.taowu.im.handler.HeartBeatHandler;
import com.whoiszxl.taowu.im.handler.NettyServerHandler;
import com.whoiszxl.taowu.im.mq.MqSenderService;
import com.whoiszxl.taowu.im.properties.ImNettyProperties;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.InetSocketAddress;
import java.util.concurrent.Callable;

/**
 * netty服务
 * @author whoiszxl
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class NettyServer implements Callable<Channel> {

    private Channel channel;
    private final EventLoopGroup parentGroup = new NioEventLoopGroup(2);
    private final EventLoopGroup childGroup = new NioEventLoopGroup();

    private final ImNettyProperties imNettyProperties;

    private final RedisUtils redisUtils;

    private final MqSenderService mqSenderService;

    private final PermissionCheckFeign permissionCheckFeign;

    @Override
    public Channel call() {
        ChannelFuture channelFuture = null;
        try{
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(parentGroup, childGroup);
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.option(ChannelOption.SO_BACKLOG, 1024);
            bootstrap.option(ChannelOption.SO_REUSEADDR, true);
            bootstrap.childOption(ChannelOption.TCP_NODELAY, true);
            bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
            bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) {
                    ch.pipeline().addLast(new MessageDecoder());
                    ch.pipeline().addLast(new MessageEncoder());
                    ch.pipeline().addLast(new IdleStateHandler(0, 0, 1));
                    ch.pipeline().addLast(new HeartBeatHandler(imNettyProperties.getTimeoutMs(), redisUtils));
                    ch.pipeline().addLast(new NettyServerHandler(
                            redisUtils,
                            mqSenderService,
                            imNettyProperties.getNodeId(),
                            permissionCheckFeign));
                }
            });

            channelFuture = bootstrap.bind(new InetSocketAddress(imNettyProperties.getTcpPort())).syncUninterruptibly();
            this.channel = channelFuture.channel();
        }catch (Exception e) {
            log.error("NettyServer|netty服务启动失败|", e);
        }finally {
            if(channelFuture != null && channelFuture.isSuccess()) {
                log.info("NettyServer启动成功!");
            }else {
                log.info("NettyServer启动失败!");
            }
        }
        return channel;
    }

    /**
     * 销毁连接
     */
    public void destroy() {
        if(channel == null) {
            return;
        }

        channel.close();
        parentGroup.shutdownGracefully();
        childGroup.shutdownGracefully();
    }


    /**
     * 获取连接通道
     * @return netty 连接通道
     */
    public Channel channel() {
        return channel;
    }
}
