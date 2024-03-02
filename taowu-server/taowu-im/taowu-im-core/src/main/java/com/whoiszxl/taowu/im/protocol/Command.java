package com.whoiszxl.taowu.im.protocol;


import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 命令常量值
 * @author whoiszxl
 */
@Schema(description = "命令常量值")
public interface Command {

    @Schema(description = "添加好友")
    Integer ADD_FRIEND = 1001;

    @Schema(description = "登录，建立连接")
    Integer LOGIN = 1002;

    @Schema(description = "登出，关闭连接")
    Integer LOGOUT = 1003;

    @Schema(description = "发送心跳")
    Integer HEART_BEAT = 1004;

    @Schema(description = "私聊相关状态")
    interface MessageCommand {

        @Schema(description = "私聊")
        Integer PRIVATE_CHAT = 2001;

        @Schema(description = "私聊ACK")
        Integer PRIVATE_CHAT_ACK = 2002;

        @Schema(description = "好友收到消息发送的ACK")
        Integer PRIVATE_CHAT_RECEIVE_ACK = 2003;

        @Schema(description = "消息已读后发送此状态")
        Integer PRIVATE_CHAT_READ = 2004;

        @Schema(description = "消息已读后将已读状态同步给其他登录的客户端")
        Integer PRIVATE_CHAT_READ_SYNC = 2005;

        @Schema(description = "消息已读后将已读状态发送给发送者的所有客户端")
        Integer PRIVATE_CHAT_READ_TELL_SEND = 2006;
    }

    @Schema(description = "群组相关状态")
    interface GroupCommand {

        @Schema(description = "群聊")
        Integer GROUP_CHAT = 3001;

        @Schema(description = "群聊ACK")
        Integer GROUP_CHAT_ACK = 3002;

    }

}
