package com.whoiszxl.taowu.im.codec;

import cn.hutool.json.JSONUtil;
import com.whoiszxl.taowu.im.pack.MessagePack;
import com.whoiszxl.taowu.im.protocol.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;

import java.util.List;


/**
 * 消息解码器
 * clientType
 * imei length
 * 接收客户端发送过来的二进制消息进行解码
 * [    int   ] [    byte  ] [    int   ] [    int   ] [    int    ]
 * [  command ] [clientType] [tokenLength] [imeiLength] [bodyLength] [tokenData] [imeiData] [bodyData]
 * @author whoiszxl
 */
public class WebSocketDecoder extends MessageToMessageDecoder<BinaryWebSocketFrame> {

    private final static Integer MESSAGE_MIN_LENGTH = 15;

    /**
     * 当客户端连接到当前TCP服务，发送一段消息之后，此方法会首先接收到客户端发送过来的消息。
     * 收到消息之后，需要对消息进行读取解析，先获取消息头，再获取消息体。
     * 然后封装为 Message 对象，以便后续进行业务处理。
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, BinaryWebSocketFrame msg, List<Object> out) throws Exception {
        ByteBuf in = msg.content();

        if(in.readableBytes() < MESSAGE_MIN_LENGTH) {
            return;
        }

        //1. 获取消息头信息
        int command = in.readInt();
        byte clientType = in.readByte();
        int tokenLength = in.readInt();
        int imeiLength = in.readInt();
        int bodyLength = in.readInt();

        // 如果消息体的长度小于imei+body的长度，则将读取索引更新为之前标记的地方
        if(in.readableBytes() < tokenLength + imeiLength + bodyLength) {
            in.resetReaderIndex();
            return;
        }

        // 获取 TOKEN 信息
        byte[] tokenData = new byte[tokenLength];
        in.readBytes(tokenData);
        String token = new String(tokenData);

        //2. 获取 IMEI 信息
        byte[] imeiData = new byte[imeiLength];
        in.readBytes(imeiData);
        String imei = new String(imeiData);

        //3. 获取消息体信息
        byte[] bodyData = new byte[bodyLength];
        in.readBytes(bodyData);
        String body = new String(bodyData);

        //4. 将获取的JSON消息体转为Packet对象，方面后续处理
        Packet packet = JSONUtil.toBean(body, Packet.get(command));

        //5. 包装为Message返回
        MessagePack messagePack = new MessagePack();
        messagePack.setCommand(command);
        messagePack.setClientType(clientType);
        messagePack.setToken(token);
        messagePack.setImei(imei);
        messagePack.setDataPack(packet);

        // 标记读取到了什么位置
        in.markReaderIndex();
        out.add(messagePack);
    }
}
