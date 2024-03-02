package com.whoiszxl.taowu.im.pack;

import com.whoiszxl.taowu.im.protocol.Command;
import com.whoiszxl.taowu.im.protocol.Packet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 发送消息ACK的数据包
 * @author whoiszxl
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageAckPack extends Packet {

    /**
     * 消息ID
     */
    private String messageId;

    @Override
    public Integer getCommand() {
        return Command.MessageCommand.PRIVATE_CHAT;
    }
}
