package com.whoiszxl.taowu.im.codec;

import cn.hutool.json.JSONUtil;
import com.whoiszxl.taowu.im.protocol.ChatMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MessageEncoder extends MessageToByteEncoder {
    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        if(msg instanceof ChatMessage) {
            ChatMessage chatMessage = (ChatMessage) msg;
            String json = JSONUtil.toJsonStr(chatMessage.getData());
            byte[] bytes = json.getBytes();

            out.writeInt(chatMessage.getCommand());
            out.writeInt(bytes.length);
            out.writeBytes(bytes);
        }

    }
}
