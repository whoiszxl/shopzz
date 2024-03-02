package com.whoiszxl.taowu.im.protocol;

import com.whoiszxl.taowu.im.pack.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 协议包
 * @author whoiszxl
 */
public abstract class Packet {

    /**
     * packet类型map
     */
    private final static Map<Integer, Class<? extends Packet>> PACKET_TYPE_MAP = new ConcurrentHashMap<>();

    static {
        PACKET_TYPE_MAP.put(Command.ADD_FRIEND, AddFriendPack.class);
        PACKET_TYPE_MAP.put(Command.LOGIN, LoginPack.class);
        PACKET_TYPE_MAP.put(Command.LOGOUT, LogoutPack.class);
        PACKET_TYPE_MAP.put(Command.HEART_BEAT, LogoutPack.class);
        PACKET_TYPE_MAP.put(Command.MessageCommand.PRIVATE_CHAT, PrivateChatPack.class);
        PACKET_TYPE_MAP.put(Command.GroupCommand.GROUP_CHAT, GroupChatPack.class);
    }

    public static Class<? extends Packet> get(Integer command) {
        return PACKET_TYPE_MAP.get(command);
    }

    /**
     * 获取命令
     * @return 命令
     */
    public abstract Integer getCommand();

}
