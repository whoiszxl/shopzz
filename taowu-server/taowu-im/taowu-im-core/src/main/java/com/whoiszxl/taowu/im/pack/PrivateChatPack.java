package com.whoiszxl.taowu.im.pack;

import com.whoiszxl.taowu.im.protocol.Command;
import com.whoiszxl.taowu.im.protocol.Packet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 发送消息的数据包
 * @author whoiszxl
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PrivateChatPack extends Packet {

    /**
     * 消息ID
     */
    private String messageId;

    /**
     * 内容ID
     */
    private String contentId;

    /**
     * 发送者ID
     */
    private Long fromMemberId;

    /**
     * 接收者ID
     */
    private Long toMemberId;

    /**
     * 消息体
     */
    private String body;

    /**
     * 消息序列号
     */
    private Long sequence;


    @Override
    public Integer getCommand() {
        return Command.MessageCommand.PRIVATE_CHAT;
    }
}
