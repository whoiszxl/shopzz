package com.whoiszxl.taowu.im.mq.consumer;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.whoiszxl.taowu.im.constants.KafkaMQConstants;
import com.whoiszxl.taowu.im.pack.MessagePack;
import com.whoiszxl.taowu.im.pack.PrivateChatPack;
import com.whoiszxl.taowu.im.processor.PrivateChatProcessor;
import com.whoiszxl.taowu.im.protocol.Command;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PrivateChatConsumer {

    private final PrivateChatProcessor privateChatProcessor;

    @KafkaListener(topics = KafkaMQConstants.IM_NETTY_TO_MESSAGE_TOPIC , groupId = "im-group")
    public void onMessage(String s) {

        if(StringUtils.isBlank(s)) {
            log.info("ImConsumer|消息消费失败|{}", s);
            return;
        }

        JSONObject jsonObject = JSONUtil.parseObj(s);
        Object dataPackObj = jsonObject.get("dataPack");
        jsonObject.remove("dataPack");

        MessagePack messagePack = JSONUtil.toBean(jsonObject, MessagePack.class);
        PrivateChatPack privateChatPack = JSONUtil.toBean(dataPackObj.toString(), PrivateChatPack.class);
        messagePack.setDataPack(privateChatPack);

        if(ObjUtil.equal(messagePack.getCommand(), Command.MessageCommand.PRIVATE_CHAT)) {
            //第一次接收到私聊消息时进行处理
            privateChatProcessor.process(messagePack);
            return;
        }

        if(ObjUtil.equal(messagePack.getCommand(), Command.MessageCommand.PRIVATE_CHAT_RECEIVE_ACK)) {
            //当接收用户接收到消息后发送ACK到发送用户
            privateChatProcessor.receiveAck(messagePack);
            return;
        }

        if(ObjUtil.equal(messagePack.getCommand(), Command.MessageCommand.PRIVATE_CHAT_READ)) {
            //当接收用户阅读了消息之后
            privateChatProcessor.read(messagePack);
            return;
        }
    }
}