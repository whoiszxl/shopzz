package com.whoiszxl.taowu.im.server;

import com.whoiszxl.taowu.common.utils.RedisUtils;
import com.whoiszxl.taowu.im.codec.WebSocketDecoder;
import com.whoiszxl.taowu.im.codec.WebSocketEncoder;
import com.whoiszxl.taowu.im.feign.PermissionCheckFeign;
import com.whoiszxl.taowu.im.handler.NettyServerHandler;
import com.whoiszxl.taowu.im.mq.KafkaProducerService;
import com.whoiszxl.taowu.im.mq.MqSenderService;
import com.whoiszxl.taowu.im.properties.ImNettyProperties;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.Callable;

/**
 * Netty Socket服务
 * @author whoiszxl
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class WebSocketServer implements Callable<Channel> {

    private Channel channel;
    private final EventLoopGroup parentGroup = new NioEventLoopGroup(2);
    private final EventLoopGroup childGroup = new NioEventLoopGroup();

    private final ImNettyProperties imNettyProperties;

    private final RedisUtils redisUtils;

    private final MqSenderService mqSenderService;

    private final KafkaProducerService kafkaProducerService;

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
                    ChannelPipeline pipeline = ch.pipeline();
                    // HTTP 编解码器
                    pipeline.addLast("http-codec", new HttpServerCodec());
                    // 将多个 Http 消息转为单一的 FullHttpRequest 或 FullHttpResponse 对象
                    pipeline.addLast("aggregator", new HttpObjectAggregator(65535));
                    // 写大数据流
                    pipeline.addLast("http-chunked", new ChunkedWriteHandler());
                    // WebSocket 协议处理器，在这里处理握手、ping、pong 等消息
                    pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
                    // 解码器，接收到客户端的消息后先进入此逻辑
                    pipeline.addLast(new WebSocketDecoder());
                    // 编码器，当业务处理完成后进入此逻辑完成编码返回到客户端的WebSocket中
                    pipeline.addLast(new WebSocketEncoder());

                    // 自定义 WebSocket 消息处理器
                    ch.pipeline().addLast(new NettyServerHandler(
                            redisUtils,
                            mqSenderService,
                            imNettyProperties.getNodeId(),
                            permissionCheckFeign));


                }
            });

            channelFuture = bootstrap.bind(imNettyProperties.getWebsocketPort());
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
