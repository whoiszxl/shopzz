package com.whoiszxl.taowu.im.service;

import com.whoiszxl.taowu.im.cqrs.query.OfflineListQuery;
import com.whoiszxl.taowu.im.entity.Message;
import com.whoiszxl.taowu.im.pack.MessagePack;
import com.whoiszxl.taowu.im.protocol.ChatMessage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 消息表 服务类
 * </p>
 *
 * @author whoiszxl
 * @since 2023-08-17
 */
public interface IMessageService extends IService<Message> {


    /**
     * 持久化私聊消息
     * @param messagePack 消息体
     */
    Long savePrivateChatMessage(MessagePack messagePack);

    /**
     * 持久化群聊消息
     * @param messagePack 消息体
     */
    void saveGroupChatMessage(MessagePack messagePack);

    /**
     * 离线消息持久化
     * @param messagePack 消息体
     * @param contentId 私聊消息体的ID
     */
    void saveOfflineMessage(MessagePack messagePack, Long contentId);

    /**
     * 获取离线消息列表
     */
    List<ChatMessage> listOfflineMessage(OfflineListQuery query);

}
