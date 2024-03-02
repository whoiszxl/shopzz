package com.whoiszxl.taowu.im.pack;

import com.whoiszxl.taowu.im.protocol.Packet;
import lombok.Data;

/**
 * 消息
 * @author whoiszxl
 */
@Data
public class MessagePack {

    private Integer command;

    private byte clientType;

    private String imei;

    private String token;

    private Packet dataPack;

}
