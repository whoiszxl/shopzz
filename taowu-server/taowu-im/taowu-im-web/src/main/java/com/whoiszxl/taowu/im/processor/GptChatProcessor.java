package com.whoiszxl.taowu.im.processor;

import cn.hutool.core.thread.ExecutorBuilder;
import cn.hutool.core.thread.ThreadFactoryBuilder;
import com.whoiszxl.taowu.im.constants.AckIsServerReceiveStatusEnum;
import com.whoiszxl.taowu.im.constants.AckStatusEnum;
import com.whoiszxl.taowu.im.constants.SequenceConstants;
import com.whoiszxl.taowu.im.idempotent.MessageIdempotentService;
import com.whoiszxl.taowu.im.protocol.Command;
import com.whoiszxl.taowu.im.mq.producer.ChatProducer;
import com.whoiszxl.taowu.im.pack.MessagePack;
import com.whoiszxl.taowu.im.pack.PrivateChatPack;
import com.whoiszxl.taowu.im.pack.PrivateChatReadPack;
import com.whoiszxl.taowu.im.pack.PrivateChatReceiveAckPack;
import com.whoiszxl.taowu.im.sequence.SequenceService;
import com.whoiszxl.taowu.im.service.IMessageService;
import com.whoiszxl.taowu.im.service.ITalkService;
import com.whoiszxl.zhipin.tools.gpt.cqrs.dto.Message;
import com.whoiszxl.zhipin.tools.gpt.cqrs.request.ChatCommand;
import com.whoiszxl.zhipin.tools.gpt.cqrs.response.ChatResponse;
import com.whoiszxl.zhipin.tools.gpt.session.GptSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author whoiszxl
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class GptChatProcessor {

    private final ChatProducer chatProducer;

    private final IMessageService messageService;

    private final SequenceService sequenceService;

    private final MessageIdempotentService messageIdempotentService;

    private final ITalkService talkService;

    private final GptSession gptSession;

    private final ThreadPoolExecutor threadPoolExecutor = ExecutorBuilder.create()
            .setCorePoolSize(3)
            .setMaxPoolSize(6)
            .setWorkQueue(new LinkedBlockingQueue<>(1024))
            .setKeepAliveTime(60, TimeUnit.SECONDS)
            .setHandler(new ThreadPoolExecutor.AbortPolicy())
            .setThreadFactory(ThreadFactoryBuilder.create().setNamePrefix("private-chat-processor-").build())
            .build();

    /**
     * 私聊消息处理
     * @param messagePack 消息体
     */
    public void process(MessagePack messagePack) {
        PrivateChatPack privateChatPack = (PrivateChatPack) messagePack.getDataPack();

        Long fromMemberId = privateChatPack.getFromMemberId();
        Long toMemberId = privateChatPack.getToMemberId();

        //防止重复提交，保证消息幂等性，先从缓存中获取，如果缓存中存在说明已经发送成功了一次，在一定间隔时间内不允许再次发送
        PrivateChatPack privateChatCache = messageIdempotentService.getPrivateChatMessageCache(privateChatPack);
        if(privateChatCache != null) {
            return;
        }

        //针对当前的会话生成有序的序列号
        Long sequence = sequenceService.getPrivateChatSequenceId(SequenceConstants.PRIVATE_CHAT_SEQUENCE, fromMemberId, toMemberId);
        privateChatPack.setSequence(sequence);

        threadPoolExecutor.execute(() -> {
            //TODO 异步持久化消息
            Long contentId = messageService.savePrivateChatMessage(messagePack);
            privateChatPack.setContentId(contentId.toString());

            //发送成功ack
            ack(privateChatPack, AckStatusEnum.SUCCESS);
            //同步消息到当前用户的其他端
            chatProducer.sendToMemberOtherClient(fromMemberId, messagePack, privateChatPack, Command.MessageCommand.PRIVATE_CHAT);
            //将当前消息添加到缓存中
            messageIdempotentService.setPrivateChatMessageCache(privateChatPack);



            //调用ChatGPT获取聊天信息
            ChatCommand command = ChatCommand.builder()
                    .messages(new ArrayList<>())
                    .user("whoiszxl")
                    .max_tokens(110)
                    .build();
            command.getMessages().add(Message.builder().role("user").content(privateChatPack.getBody()).build());
            ChatResponse chatResponse = gptSession.chat(command);
            String talkResponse = chatResponse.getChoices().get(0).getMessage().getContent();
            log.info("gpt聊天结果:{}", talkResponse);


            privateChatPack.setBody(talkResponse);
            privateChatPack.setToMemberId(privateChatPack.getFromMemberId());
            privateChatPack.setFromMemberId(-1L);
            messagePack.setDataPack(privateChatPack);
            contentId = messageService.savePrivateChatMessage(messagePack);
            privateChatPack.setContentId(contentId.toString());

            chatProducer.sendToMemberAllClient(
                    fromMemberId,
                    privateChatPack,
                    Command.MessageCommand.PRIVATE_CHAT,
                    AckStatusEnum.SUCCESS);

        });
    }

    private void receiveAck(MessagePack messagePack, PrivateChatPack privateChatPack, AckStatusEnum ackStatusEnum) {
        PrivateChatReceiveAckPack privateChatReceiveAckPack = PrivateChatReceiveAckPack.builder()
                .fromMemberId(privateChatPack.getToMemberId())
                .toMemberId(privateChatPack.getFromMemberId())
                .messageId(privateChatPack.getMessageId())
                .sequence(privateChatPack.getSequence())
                .isServer(AckIsServerReceiveStatusEnum.TRUE.getCode())
                .build();
        chatProducer.sendToMember(
                privateChatPack.getFromMemberId(),
                messagePack.getClientType(),
                messagePack.getImei(),
                Command.MessageCommand.PRIVATE_CHAT_RECEIVE_ACK,
                privateChatReceiveAckPack,
                ackStatusEnum);
    }


    private void ack(PrivateChatPack privateChatPack, AckStatusEnum ackStatusEnum) {
        chatProducer.sendToMemberAllClient(
                privateChatPack.getFromMemberId(),
                privateChatPack,
                Command.MessageCommand.PRIVATE_CHAT_ACK,
                ackStatusEnum);
    }

    public void receiveAck(MessagePack messagePack) {
        PrivateChatReceiveAckPack dataPack = (PrivateChatReceiveAckPack) messagePack.getDataPack();
        chatProducer.sendToMemberAllClient(dataPack.getFromMemberId(), dataPack, Command.MessageCommand.PRIVATE_CHAT, AckStatusEnum.SUCCESS);
    }

    public void read(MessagePack messagePack) {
        PrivateChatReadPack dataPack = (PrivateChatReadPack) messagePack.getDataPack();
        //修改对话框中读到了什么位置
        talkService.read(dataPack);
        //发送已读状态给自己的其他客户端进行同步
        chatProducer.sendToMemberOtherClient(dataPack.getFromMemberId(), messagePack, dataPack, Command.MessageCommand.PRIVATE_CHAT_READ_SYNC);
        //发送已读消息给发送者的客户端进行同步
        chatProducer.sendToMemberAllClient(dataPack.getToMemberId(), dataPack, Command.MessageCommand.PRIVATE_CHAT_READ_TELL_SEND, AckStatusEnum.SUCCESS);
    }

}
