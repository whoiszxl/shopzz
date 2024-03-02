package com.whoiszxl.zhipin.im.handler;

import com.whoiszxl.zhipin.im.constants.FieldConstants;
import com.whoiszxl.zhipin.im.session.ChannelHolder;
import com.whoiszxl.zhipin.tools.common.utils.RedisUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

/**
 * 心跳处理器
 * @author whoiszxl
 */
@Slf4j
public class HeartBeatHandler extends ChannelInboundHandlerAdapter {

    private final Integer timeoutMs;

    private final RedisUtils redisUtils;

    public HeartBeatHandler(Integer timeoutMs, RedisUtils redisUtils) {
        this.timeoutMs = timeoutMs;
        this.redisUtils = redisUtils;
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if(event.state() == IdleState.ALL_IDLE) {
                Long heartBeatTime = (Long) ctx.channel().attr(AttributeKey.valueOf(FieldConstants.HEART_BEAT)).get();

                //校验当前时间是否超过了超时时间，超过了就执行下线操作
                if(heartBeatTime != null && System.currentTimeMillis() - heartBeatTime > timeoutMs) {
                    ChannelHolder.offlineSession(redisUtils, ctx);
                }
            }
        }
    }
}
