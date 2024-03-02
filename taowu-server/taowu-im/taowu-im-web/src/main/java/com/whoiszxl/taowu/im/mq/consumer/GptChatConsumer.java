package com.whoiszxl.taowu.im.mq.consumer;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.whoiszxl.taowu.im.constants.KafkaMQConstants;
import com.whoiszxl.taowu.im.pack.MessagePack;
import com.whoiszxl.taowu.im.pack.PrivateChatPack;
import com.whoiszxl.taowu.im.processor.GptChatProcessor;
import com.whoiszxl.taowu.im.sequence.SequenceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class GptChatConsumer {


    private final GptChatProcessor gptChatProcessor;

    private final SequenceService sequenceService;



    @KafkaListener(topics = KafkaMQConstants.IM_NETTY_TO_GPT_TOPIC , groupId = "im-group")
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

        gptChatProcessor.process(messagePack);

    }


}