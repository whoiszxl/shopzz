package com.whoiszxl.taowu.im.pack;

import com.whoiszxl.taowu.im.protocol.Command;
import com.whoiszxl.taowu.im.protocol.Packet;
import lombok.Data;

/**
 * 登出的数据包
 * @author whoiszxl
 */
@Data
public class LogoutPack extends Packet {

    /**
     * 登录的账号ID
     */
    private String memberId;

    @Override
    public Integer getCommand() {
        return Command.LOGOUT;
    }
}
