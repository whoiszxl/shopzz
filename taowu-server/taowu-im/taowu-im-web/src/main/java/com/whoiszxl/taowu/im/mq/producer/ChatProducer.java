package com.whoiszxl.taowu.im.mq.producer;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.json.JSONUtil;
import com.whoiszxl.taowu.im.constants.AckStatusEnum;
import com.whoiszxl.taowu.im.constants.KafkaMQConstants;
import com.whoiszxl.taowu.im.entity.MemberSession;
import com.whoiszxl.taowu.im.pack.GroupChatPack;
import com.whoiszxl.taowu.im.pack.MessagePack;
import com.whoiszxl.taowu.im.protocol.ChatMessage;
import com.whoiszxl.taowu.im.protocol.Command;
import com.whoiszxl.taowu.im.protocol.Packet;
import com.whoiszxl.taowu.im.session.MemberSessionHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatProducer {

    private final MqSenderService mqSenderService;

    private final MemberSessionHolder memberSessionHolder;

    /**
     * 发送消息给指定的用户的所有客户端
     */
    public List<MemberSession> sendToMemberAllClient(
            Long memberId,
            Packet packet,
            Integer command,
            AckStatusEnum ackStatusEnum) {
        List<MemberSession> toMemberSessionList = memberSessionHolder.getAllMemberSession(memberId);

        List<MemberSession> successList = new ArrayList<>();
        for (MemberSession memberSession : toMemberSessionList) {
            boolean sendFlag = sendPacket(memberId,
                    command,
                    packet,
                    memberSession,
                    ackStatusEnum);

            if(sendFlag) successList.add(memberSession);
        }
        return successList;
    }


    /**
     * 发送消息给指定的用户的指定客户端
     */
    public void sendToMember(
            Long memberId,
            Byte clientType,
            String imei,
            Integer command,
            Packet packet,
            AckStatusEnum ackStatusEnum) {
        MemberSession memberSession = memberSessionHolder.getMemberSession(memberId,clientType, imei);
        sendPacket(memberId, command, packet, memberSession, ackStatusEnum);

    }

    private boolean sendPacket(Long memberId, Integer command, Packet packet, MemberSession memberSession, AckStatusEnum ackStatusEnum) {
        ChatMessage<Object> chatMessage = ChatMessage.builder()
                .toMemberId(String.valueOf(memberId))
                .command(command)
                .clientType(memberSession.getClientType())
                .imei(memberSession.getImei())
                .data(packet)
                .ackStatus(ackStatusEnum.getCode())
                .sendAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();

        return mqSenderService.sendMessage(KafkaMQConstants.IM_MESSAGE_TO_NETTY_TOPIC, JSONUtil.toJsonStr(chatMessage));
    }

    public void sendToMemberOtherClient(Long memberId, MessagePack messagePack, Packet packet, Integer command) {
        List<MemberSession> fromMemberSessionList = memberSessionHolder.getAllMemberSession(memberId);
        for (MemberSession memberSession : fromMemberSessionList) {
            if(memberSession.getClientType() != messagePack.getClientType() &&
                    ObjUtil.notEqual(memberSession.getImei(), messagePack.getImei())) {
                sendPacket(memberId,
                        command,
                        packet,
                        memberSession,
                        AckStatusEnum.SUCCESS);
            }
        }
    }

    public void sendToMemberOtherClientGroup(Long memberId, MessagePack messagePack) {
        GroupChatPack groupChatPack = (GroupChatPack) messagePack.getDataPack();
        List<MemberSession> fromMemberSessionList = memberSessionHolder.getAllMemberSession(groupChatPack.getFromMemberId());
        for (MemberSession memberSession : fromMemberSessionList) {
            if(memberSession.getClientType() != messagePack.getClientType() &&
                    ObjUtil.notEqual(memberSession.getImei(), messagePack.getImei())) {
                sendPacket(memberId,
                        Command.MessageCommand.PRIVATE_CHAT,
                        groupChatPack,
                        memberSession,
                        AckStatusEnum.SUCCESS);
            }
        }
    }
}
