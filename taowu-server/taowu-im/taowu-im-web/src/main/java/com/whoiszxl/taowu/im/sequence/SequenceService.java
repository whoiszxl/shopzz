package com.whoiszxl.taowu.im.sequence;

/**
 * 序列号服务
 */
public interface SequenceService {


    /**
     * 获取私聊消息在会话中的唯一自增ID
     * @param key redis的键
     * @param fromMemberId 发送者ID
     * @param toMemberId 接收者ID
     * @return 自增ID
     */
    Long getPrivateChatSequenceId(String key, Long fromMemberId, Long toMemberId);

    /**
     * 获取群聊消息在群组中的唯一自增ID
     * @param key redis的键
     * @param groupId 群组ID
     * @return 自增ID
     */
    Long getGroupChatSequenceId(String key, Long groupId);
}
