package com.whoiszxl.taowu.im.pack;

import com.whoiszxl.taowu.im.protocol.Command;
import com.whoiszxl.taowu.im.protocol.Packet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 已读消息的数据包
 * @author whoiszxl
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupChatReadPack extends Packet {

    /**
     * 对话ID
     */
    private Long talkId;

    /**
     * 群组ID
     */
    private Long groupId;

    /**
     * 发送者ID
     */
    private Long fromMemberId;

    /**
     * 接收者ID
     */
    private Long toMemberId;

    /**
     * 消息序列号
     */
    private Long sequence;

    /**
     * 对话类型: 1-单聊 2-群聊 3-ChatGPT 4-机器人
     */
    private Integer talkType;


    @Override
    public Integer getCommand() {
        return Command.MessageCommand.PRIVATE_CHAT;
    }
}
