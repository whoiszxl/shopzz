package com.whoiszxl.zhipin.im.mq.consumer;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.whoiszxl.zhipin.im.bean.ChannelAttrDto;
import com.whoiszxl.zhipin.im.constants.KafkaMQConstants;
import com.whoiszxl.zhipin.im.protocol.ChatMessage;
import com.whoiszxl.zhipin.im.session.ChannelHolder;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


/**
 * IM消费者
 * @author whoiszxl
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ImConsumer {

    /**
     * 接收到处理之后的消息需要发送到客户端去
     * @param s 处理之后的消息体
     */
    @KafkaListener(topics = KafkaMQConstants.IM_MESSAGE_TO_NETTY_TOPIC, groupId = "im-group")
    public void onMessage(String s) {
        //消息校验
        if(StrUtil.isBlank(s)) {
            log.warn("接收到服务端发送过来的消息为空: {}", s);
            return;
        }

        ChatMessage chatMessage = JSONUtil.toBean(s, ChatMessage.class);

        ChannelAttrDto channelAttr = ChannelAttrDto.builder()
                .memberId(chatMessage.getToMemberId().toString())
                .clientType(chatMessage.getClientType())
                .imei(chatMessage.getImei()).build();

        //从CHANNEL_MAP中通过会员号，客户端类型，imei号拿到需要投递的channel
        NioSocketChannel nioSocketChannel = ChannelHolder.get(channelAttr);
        if(nioSocketChannel != null) {
            //写回并刷新，此处会进入 Encode 逻辑中，通过编码后返回
            nioSocketChannel.writeAndFlush(chatMessage);
            log.info("消息写回成功: {}", chatMessage);
        }
    }
}
