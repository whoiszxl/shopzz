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
public class PrivateChatReceiveAckPack extends Packet {

    /**
     * 消息ID
     */
    private String messageId;

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
     * 是否是服务端发起的ACK 0-否 1-是
     */
    private byte isServer;

    @Override
    public Integer getCommand() {
        return Command.MessageCommand.PRIVATE_CHAT;
    }
}
