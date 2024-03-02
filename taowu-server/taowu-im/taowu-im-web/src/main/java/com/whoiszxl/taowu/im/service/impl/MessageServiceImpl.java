package com.whoiszxl.taowu.im.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.taowu.im.constants.TalkTypeEnum;
import com.whoiszxl.taowu.im.cqrs.command.TalkAddCommand;
import com.whoiszxl.taowu.im.cqrs.query.OfflineListQuery;
import com.whoiszxl.taowu.im.entity.GroupMessage;
import com.whoiszxl.taowu.im.entity.Message;
import com.whoiszxl.taowu.im.entity.MessageContent;
import com.whoiszxl.taowu.im.entity.Talk;
import com.whoiszxl.taowu.im.mapper.MessageMapper;
import com.whoiszxl.taowu.im.pack.GroupChatPack;
import com.whoiszxl.taowu.im.pack.MessagePack;
import com.whoiszxl.taowu.im.pack.PrivateChatPack;
import com.whoiszxl.taowu.im.protocol.ChatMessage;
import com.whoiszxl.taowu.im.service.IGroupMessageService;
import com.whoiszxl.taowu.im.service.IMessageContentService;
import com.whoiszxl.taowu.im.service.IMessageService;
import com.whoiszxl.taowu.im.service.ITalkService;
import com.whoiszxl.zhipin.tools.common.token.TokenHelper;
import com.whoiszxl.zhipin.tools.common.utils.RedisUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 消息表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2023-08-17
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements IMessageService {

    private final IMessageContentService messageContentService;

    private final IGroupMessageService groupMessageService;

    private final ITalkService talkService;

    private final TokenHelper tokenHelper;

    private final RedisUtils redisUtils;

    @Value("${im.offlineMessageMaxCount}")
    private Integer offlineMessageMaxCount;

    @Override
    @Transactional
    public Long savePrivateChatMessage(MessagePack messagePack) {
        PrivateChatPack privateChatPack = (PrivateChatPack) messagePack.getDataPack();

        //判断是否存在聊天框，不存在则创建
        Talk talk = talkService.getOne(Wrappers.<Talk>lambdaQuery()
                .eq(Talk::getFromMemberId, privateChatPack.getFromMemberId())
                .eq(Talk::getToMemberId, privateChatPack.getToMemberId()));
        if(talk == null) {
            talkService.add(TalkAddCommand.builder()
                    .fromMemberId(privateChatPack.getFromMemberId())
                    .toMemberId(privateChatPack.getToMemberId())
                    .talkType(TalkTypeEnum.PRIVATE_CHAT.getCode())
                    .build());
        }


        // 消息体持久化
        long contentId = IdUtil.getSnowflakeNextId();
        MessageContent messageContent = new MessageContent();
        messageContent.setId(contentId);
        messageContent.setMessageContent(privateChatPack.getBody());
        messageContentService.save(messageContent);

        //消息记录持久化
        Message messageOne = buildPrivateMessage(privateChatPack.getFromMemberId(), privateChatPack, contentId);
        Message messageTwo = buildPrivateMessage(privateChatPack.getToMemberId(), privateChatPack, contentId);
        this.saveBatch(CollUtil.newArrayList(messageOne, messageTwo));

        return contentId;
    }

    private Message buildPrivateMessage(Long ownerId, PrivateChatPack privateChatPack, Long contentId) {
        Message message = new Message();
        message.setContentId(contentId);
        message.setFromMemberId(privateChatPack.getFromMemberId());
        message.setToMemberId(privateChatPack.getToMemberId());
        message.setOwnerId(ownerId);
        message.setMessageType(0); //TODO 待实现图片类型、语音类型
        message.setSequence(privateChatPack.getSequence());
        return message;
    }


    @Override
    public void saveGroupChatMessage(MessagePack messagePack) {
        GroupChatPack groupChatPack = (GroupChatPack) messagePack.getDataPack();

        // 消息体持久化
        long contentId = IdUtil.getSnowflakeNextId();
        MessageContent messageContent = new MessageContent();
        messageContent.setId(contentId);
        messageContent.setMessageContent(groupChatPack.getBody());
        messageContentService.save(messageContent);

        //消息记录持久化
        GroupMessage groupMessage = buildGroupMessage(groupChatPack, contentId);
        groupMessageService.save(groupMessage);

    }

    @Override
    public void saveOfflineMessage(MessagePack messagePack, Long contentId) {
        PrivateChatPack privateChatPack = (PrivateChatPack) messagePack.getDataPack();

        ChatMessage<Object> chatMessage = ChatMessage.builder()
                .toMemberId(String.valueOf(privateChatPack.getToMemberId()))
                .command(privateChatPack.getCommand())
                .clientType((byte) 0)
                .imei("todo")
                .data(privateChatPack)
                .sendAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();

        String key = String.format("%s:%s", "offlineMessage", privateChatPack.getToMemberId());
        Long size = redisUtils.zSize(key);
        if(size > offlineMessageMaxCount) {
            redisUtils.zRemoveRange(key, 0, 0);
        }

        redisUtils.zAdd(key, JSONUtil.toJsonStr(chatMessage), privateChatPack.getSequence());
    }

    @Override
    public List<ChatMessage> listOfflineMessage(OfflineListQuery query) {
        String key = String.format("%s:%s", "offlineMessage", tokenHelper.getAppMemberId());
        Set<String> setList = redisUtils.zRangeByScore(key, Double.parseDouble(query.getClientSequence()));
        List<ChatMessage> chatMessages = new ArrayList<>();
        for (String s : setList) {
            chatMessages.add(JSONUtil.toBean(s, ChatMessage.class));
        }
        return chatMessages;
    }

    private GroupMessage buildGroupMessage(GroupChatPack groupChatPack, long contentId) {
        GroupMessage groupMessage = new GroupMessage();
        groupMessage.setId(IdUtil.getSnowflakeNextId());
        groupMessage.setGroupId(groupChatPack.getGroupId());
        groupMessage.setContentId(contentId);
        groupMessage.setOwnerMemberId(groupChatPack.getFromMemberId());
        groupMessage.setMessageType(0);  //TODO 待实现图片类型、语音类型
        groupMessage.setSequence(groupMessage.getSequence());
        return groupMessage;
    }
}
