package com.whoiszxl.zhipin.im.handler;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.json.JSONUtil;
import com.whoiszxl.taowu.common.entity.ResponseResult;
import com.whoiszxl.taowu.common.utils.RedisUtils;
import com.whoiszxl.taowu.im.constants.ConnectStatusEnum;
import com.whoiszxl.taowu.im.constants.FieldConstants;
import com.whoiszxl.taowu.im.constants.ImRedisKeysEnum;
import com.whoiszxl.taowu.im.constants.KafkaMQConstants;
import com.whoiszxl.taowu.im.dto.CheckGroupChatPermissionQuery;
import com.whoiszxl.taowu.im.entity.MemberSession;
import com.whoiszxl.taowu.im.feign.PermissionCheckFeign;
import com.whoiszxl.taowu.im.pack.GroupChatPack;
import com.whoiszxl.taowu.im.pack.MessagePack;
import com.whoiszxl.taowu.im.pack.PrivateChatPack;
import com.whoiszxl.taowu.im.protocol.Command;
import com.whoiszxl.zhipin.im.bean.ChannelAttrDto;
import com.whoiszxl.zhipin.im.mq.MqSenderService;
import com.whoiszxl.zhipin.im.session.ChannelHolder;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;

/**
 * netty im聊天处理器
 * @author whoiszxl
 */
@Slf4j
public class NettyServerHandler extends SimpleChannelInboundHandler<MessagePack> {

    private final RedisUtils redisUtils;

    private final MqSenderService mqSenderService;

    private final String nodeId;

    private final PermissionCheckFeign permissionCheckFeign;

    public NettyServerHandler(RedisUtils redisUtils, MqSenderService mqSenderService, String nodeId, PermissionCheckFeign permissionCheckFeign) {
        this.redisUtils = redisUtils;
        this.mqSenderService = mqSenderService;
        this.nodeId = nodeId;
        this.permissionCheckFeign = permissionCheckFeign;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessagePack msg) throws Exception {
        Integer commandType = msg.getCommand();

        //token鉴权，判断是否为合法用户
        String token = msg.getToken();
        Object loginIdByToken = StpUtil.getLoginIdByToken(token);
        if(loginIdByToken == null) {
            log.info("token鉴权错误");
            return;
        }
        String memberId = (String)loginIdByToken;

        if(ObjUtil.equal(Command.LOGIN, commandType)) {
            log.info("用户[{}]开始进行登录操作，参数: {}", memberId, msg);
            //将账号相关信息设置到channel attr
            setInfoToChannel(ctx, memberId, msg.getClientType(), msg.getImei());

            //构建memberSession对象，保存到Redis中，便于其他服务获取使用
            InetAddress localHost = InetAddress.getLocalHost();
            MemberSession memberSession = MemberSession.builder()
                    .memberId(memberId)
                    .clientType(msg.getClientType())
                    .imei(msg.getImei())
                    .nodeId(nodeId)
                    .nodeHost(localHost.getHostAddress())
                    .connectStatus(ConnectStatusEnum.ONLINE.getCode()).build();

            //将memberSession设置到Redis中
            redisUtils.hPut(
                    String.format(ImRedisKeysEnum.MEMBER_SESSION_KEY.getPrefix(), memberId),
                    String.format("%s:%s", msg.getClientType(), msg.getImei()),
                    JSONUtil.toJsonStr(memberSession));

            //将当前的连接保存到本地的ConcurrentHashMap中
            ChannelAttrDto channelAttrDto = ChannelHolder.getInfoFromChannel(ctx);
            ChannelHolder.put(channelAttrDto, (NioSocketChannel) ctx.channel());
        }

        //处理退出逻辑，删除HashMap里的连接和Redis中的session信息
        if(ObjUtil.equal(Command.LOGOUT, commandType)) {
            ChannelHolder.logoutSession(redisUtils, ctx);
        }

        //发送心跳，就是设置 HEART_BEAT 为当前时间的时间戳。如果很长时间没有接收到心跳消息，则说明用户已经退出了APP，此时需要将用户的连接断开
        if(ObjUtil.equal(Command.HEART_BEAT, commandType)) {
            ctx.channel().attr(AttributeKey.valueOf(FieldConstants.HEART_BEAT))
                    .set(System.currentTimeMillis());
        }

        //发送私聊消息
        if(ObjUtil.equal(Command.MessageCommand.PRIVATE_CHAT, commandType)) {
            PrivateChatPack privateChatPack = (PrivateChatPack) msg.getDataPack();
            privateChatPack.setFromMemberId(Long.valueOf(memberId));
            //校验是否是机器人消息
            if(privateChatPack.getToMemberId() == -1L) {
                mqSenderService.sendMessage(KafkaMQConstants.IM_NETTY_TO_GPT_TOPIC, JSONUtil.toJsonStr(msg));
                return;
            }

            //校验是否有发送权限
//            ResponseResult<Boolean> checkResult = permissionCheckFeign.checkPrivateChatPermission(CheckPrivateChatPermissionQuery
//                    .builder()
//                    .fromMemberId(privateChatPack.getFromMemberId())
//                    .toMemberId(privateChatPack.getToMemberId())
//                    .build());

            ResponseResult<Boolean> checkResult = ResponseResult.buildSuccess();
            //发送MQ，将消息发送到web服务进行分发
            if(checkResult.isOk()) {
                msg.setDataPack(privateChatPack);
                mqSenderService.sendMessage(KafkaMQConstants.IM_NETTY_TO_MESSAGE_TOPIC, JSONUtil.toJsonStr(msg));
                return;
            }

            //TODO 实现ack
        }

        //发送群聊消息
        if(ObjUtil.equal(Command.GroupCommand.GROUP_CHAT, commandType)) {
            //校验是否有发送权限
            GroupChatPack groupChatPack = (GroupChatPack) msg.getDataPack();
            ResponseResult<Boolean> checkResult = permissionCheckFeign
                    .checkGroupChatPermission(CheckGroupChatPermissionQuery.builder()
                            .fromMemberId(groupChatPack.getFromMemberId())
                            .groupId(groupChatPack.getGroupId()).build());

            //发送MQ，将消息发送到web服务进行分发
            if(checkResult.isOk()) {
                mqSenderService.sendMessage(KafkaMQConstants.IM_NETTY_TO_GROUP_TOPIC, JSONUtil.toJsonStr(msg));
                return;
            }

            //TODO 实现ack
        }

        //发送ACK消息
        if(ObjUtil.equal(Command.MessageCommand.PRIVATE_CHAT_RECEIVE_ACK, commandType)) {
            mqSenderService.sendMessage(KafkaMQConstants.IM_NETTY_TO_MESSAGE_TOPIC, JSONUtil.toJsonStr(msg));
        }

    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
    }

    private void setInfoToChannel(ChannelHandlerContext ctx, String memberId, Byte clientType, String imei) {
        ctx.channel().attr(AttributeKey.valueOf(FieldConstants.MEMBER_ID)).set(memberId);
        ctx.channel().attr(AttributeKey.valueOf(FieldConstants.CLIENT_TYPE)).set(clientType);
        ctx.channel().attr(AttributeKey.valueOf(FieldConstants.IMEI)).set(imei);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        ChannelHolder.offlineSession(redisUtils, ctx);
    }
}
