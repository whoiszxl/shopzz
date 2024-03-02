package com.whoiszxl.taowu.im.processor;

import cn.hutool.core.thread.ExecutorBuilder;
import cn.hutool.core.thread.ThreadFactoryBuilder;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.whoiszxl.taowu.im.constants.AckStatusEnum;
import com.whoiszxl.taowu.im.constants.SequenceConstants;
import com.whoiszxl.taowu.im.entity.GroupMember;
import com.whoiszxl.taowu.im.idempotent.MessageIdempotentService;
import com.whoiszxl.taowu.im.protocol.Command;
import com.whoiszxl.taowu.im.mq.producer.ChatProducer;
import com.whoiszxl.taowu.im.pack.GroupChatPack;
import com.whoiszxl.taowu.im.pack.MessagePack;
import com.whoiszxl.taowu.im.sequence.SequenceService;
import com.whoiszxl.taowu.im.service.IGroupMemberService;
import com.whoiszxl.taowu.im.service.IMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author whoiszxl
 */
@Component
@RequiredArgsConstructor
public class GroupChatProcessor {

    private final IGroupMemberService groupMemberService;

    private final ChatProducer chatProducer;

    private final IMessageService messageService;

    private final SequenceService sequenceService;

    private final MessageIdempotentService messageIdempotentService;

    private final ThreadPoolExecutor threadPoolExecutor = ExecutorBuilder.create()
            .setCorePoolSize(3)
            .setMaxPoolSize(6)
            .setWorkQueue(new LinkedBlockingQueue<>(1024))
            .setKeepAliveTime(60, TimeUnit.SECONDS)
            .setHandler(new ThreadPoolExecutor.AbortPolicy())
            .setThreadFactory(ThreadFactoryBuilder.create().setNamePrefix("group-chat-processor-").build())
            .build();
    /**
     * 私聊消息处理
     * @param messagePack 消息体
     */
    public void process(MessagePack messagePack) {
        //获取到群消息的包
        GroupChatPack groupChatPack = (GroupChatPack) messagePack.getDataPack();
        Long groupId = groupChatPack.getGroupId();
        Long fromMemberId = groupChatPack.getFromMemberId();

        //防止重复提交，保证消息幂等性，先从缓存中获取，如果缓存中存在说明已经发送成功了一次，在一定间隔时间内不允许再次发送
        GroupChatPack groupChatCache = messageIdempotentService.getGroupChatMessageCache(groupChatPack);
        if(groupChatCache != null) {
            threadPoolExecutor.execute(()  -> {
                //发送成功ack
                ack(groupChatCache, AckStatusEnum.SUCCESS);
                //同步消息到当前用户的其他端
                messagePack.setDataPack(groupChatCache);
                chatProducer.sendToMemberOtherClientGroup(fromMemberId, messagePack);
                //发送消息给目标用户的所有端
                sendToMember(groupChatCache);
            });
        }

        Long sequence = sequenceService.getGroupChatSequenceId(SequenceConstants.GROUP_CHAT_SEQUENCE, groupChatPack.getGroupId());
        groupChatPack.setSequence(sequence);

        threadPoolExecutor.execute(() -> {
            //持久化消息
            messageService.saveGroupChatMessage(messagePack);
            //发送成功ack
            ack(groupChatPack, AckStatusEnum.SUCCESS);
            //同步消息到当前用户的其他端
            chatProducer.sendToMemberOtherClientGroup(fromMemberId, messagePack);
            //发送消息给目标用户的所有端
            sendToMember(groupChatPack);

            //将当前消息添加到缓存中，防止短时间内重复发送
            messageIdempotentService.setGroupChatMessageCache(groupChatPack);
        });
    }

    private void ack(GroupChatPack groupChatPack, AckStatusEnum ackStatusEnum) {
        chatProducer.sendToMemberAllClient(
                groupChatPack.getFromMemberId(),
                groupChatPack,
                Command.GroupCommand.GROUP_CHAT_ACK,
                ackStatusEnum);
    }

    private void sendToMember(GroupChatPack groupChatPack) {
        //发送消息给目标用户的所有端
        List<GroupMember> memberList = groupMemberService.list(Wrappers.<GroupMember>lambdaQuery().eq(GroupMember::getGroupId, groupChatPack.getGroupId()));
        for (GroupMember member : memberList) {
            chatProducer.sendToMemberAllClient(member.getMemberId(), groupChatPack, Command.GroupCommand.GROUP_CHAT, AckStatusEnum.SUCCESS);
        }
    }

}
