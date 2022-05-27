package com.whoiszxl.netty;

import com.whoiszxl.cqrs.command.SmsSendCommand;
import com.whoiszxl.service.impl.SmsSendServiceImpl;
import com.whoiszxl.utils.JsonUtil;
import com.whoiszxl.utils.SpringUtils;
import com.whoiszxl.utils.StrPoolUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.net.InetSocketAddress;

/**
 * sms netty服务端处理器
 *
 * @author whoiszxl
 * @date 2022/5/27
 */
@Slf4j
public class SmsNettyServerHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        log.info("SmsNettyServerHandler|接收到消息|{}", msg);
        String response = "success";

        try{
            if(StringUtils.isBlank(msg)) {
                log.info("SmsNettyServerHandler|接收到的消息为空|");
                return;
            }
            msg = msg.trim();
            SmsSendCommand smsSendCommand = JsonUtil.fromJson(msg, SmsSendCommand.class);
            if(smsSendCommand == null
                    || StringUtils.isBlank(smsSendCommand.getMobile())
                    || StringUtils.isBlank(smsSendCommand.getSignature())
                    || StringUtils.isBlank(smsSendCommand.getTemplate())) {
                log.info("SmsNettyServerHandler|接收到的消息体不正确|");
                return;
            }

            SpringUtils.getBean(SmsSendServiceImpl.class).send(smsSendCommand);

        }catch (Exception e) {
            response = e.getMessage();
        }

        ctx.writeAndFlush(response + StrPoolUtil.NEWLINE);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        InetSocketAddress socketAddress = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIp = socketAddress.getAddress().getHostAddress();
        log.info("SmsNettyServerHandler|channelActive 收到客户端连接|{}", clientIp);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }


}
