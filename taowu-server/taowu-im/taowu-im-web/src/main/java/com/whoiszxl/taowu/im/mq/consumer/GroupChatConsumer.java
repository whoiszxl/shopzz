package com.whoiszxl.taowu.im.mq.consumer;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.whoiszxl.taowu.im.constants.KafkaMQConstants;
import com.whoiszxl.taowu.im.pack.GroupChatPack;
import com.whoiszxl.taowu.im.pack.MessagePack;
import com.whoiszxl.taowu.im.processor.GroupChatProcessor;
import com.whoiszxl.taowu.im.protocol.Command;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class GroupChatConsumer {

    private final GroupChatProcessor groupChatProcessor;

    @KafkaListener(topics = KafkaMQConstants.IM_NETTY_TO_GROUP_TOPIC , groupId = "im-group")
    public void onMessage(String s) {

        if(StringUtils.isBlank(s)) {
            log.info("ImConsumer|消息消费失败|{}", s);
            return;
        }

        JSONObject jsonObject = JSONUtil.parseObj(s);
        Object dataPackObj = jsonObject.get("dataPack");
        jsonObject.remove("dataPack");

        MessagePack messagePack = JSONUtil.toBean(jsonObject, MessagePack.class);
        GroupChatPack groupChatPack = JSONUtil.toBean(dataPackObj.toString(), GroupChatPack.class);
        messagePack.setDataPack(groupChatPack);

        if(ObjUtil.equal(messagePack.getCommand(), Command.GroupCommand.GROUP_CHAT)) {
            groupChatProcessor.process(messagePack);
            return;
        }
    }
}