package com.whoiszxl.zhipin.im.session;

import cn.hutool.json.JSONUtil;
import com.whoiszxl.taowu.common.utils.RedisUtils;
import com.whoiszxl.taowu.im.constants.ConnectStatusEnum;
import com.whoiszxl.taowu.im.constants.FieldConstants;
import com.whoiszxl.taowu.im.constants.ImRedisKeysEnum;
import com.whoiszxl.taowu.im.entity.MemberSession;
import com.whoiszxl.zhipin.im.bean.ChannelAttrDto;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * channel持有管理
 * @author whoiszxl
 */
@Component
public class ChannelHolder {

    /**
     * 存储用户的Channel连接
     */
    private static final Map<ChannelAttrDto, NioSocketChannel> CHANNEL_MAP = new ConcurrentHashMap<>();

    /**
     * 新增用户的channel连接
     * @param dto 用户的基本连接信息
     * @param channel channel连接
     */
    public static void put(ChannelAttrDto dto, NioSocketChannel channel) {
        CHANNEL_MAP.put(dto, channel);
    }

    /**
     * 获取用户的channel连接
     * @param dto 用户的基本连接信息
     * @return channel连接
     */
    public static NioSocketChannel get(ChannelAttrDto dto) {
        return CHANNEL_MAP.get(dto);
    }

    /**
     * 移除用户的channel连接
     * @param dto 用户的基本连接信息
     */
    public static void remove(ChannelAttrDto dto) {
        CHANNEL_MAP.remove(dto);
    }

    /**
     * 从channel连接中获取用户的基本连接信息
     * @param ctx channel处理器上下文
     * @return 用户的基本连接信息
     */
    public static ChannelAttrDto getInfoFromChannel(ChannelHandlerContext ctx) {
        String memberId = (String) ctx.channel().attr(AttributeKey.valueOf(FieldConstants.MEMBER_ID)).get();
        Byte clientType = (byte) ctx.channel().attr(AttributeKey.valueOf(FieldConstants.CLIENT_TYPE)).get();
        String imei = (String) ctx.channel().attr(AttributeKey.valueOf(FieldConstants.IMEI)).get();
        return ChannelAttrDto.builder().memberId(memberId).clientType(clientType).imei(imei).build();
    }

    /**
     * 注销处理
     * @param redisUtils redis工具类
     * @param ctx channel
     */
    public static void logoutSession(RedisUtils redisUtils, ChannelHandlerContext ctx) {
        ChannelAttrDto channelAttrDto = ChannelHolder.getInfoFromChannel(ctx);
        ChannelHolder.remove(channelAttrDto);

        redisUtils.hDel(
                String.format(ImRedisKeysEnum.MEMBER_SESSION_KEY.getPrefix(), channelAttrDto.getMemberId()),
                String.valueOf(channelAttrDto.getClientType()));
        ctx.channel().close();

        //TODO 发送消息提醒其他好友我已经下线了
    }

    /**
     * 离线处理
     * @param redisUtils redis工具类
     * @param ctx channel
     */
    public static void offlineSession(RedisUtils redisUtils, ChannelHandlerContext ctx) {
        //拿到当前连接的基础属性: 用户ID、客户端类型、设备IMEI号 后移除对应的 channel
        ChannelAttrDto channelAttrDto = ChannelHolder.getInfoFromChannel(ctx);
        ChannelHolder.remove(channelAttrDto);

        String key = String.format(ImRedisKeysEnum.MEMBER_SESSION_KEY.getPrefix(), channelAttrDto.getMemberId());
        String field = String.format("%s:%s", channelAttrDto.getClientType(), channelAttrDto.getImei());
        //从Redis中拿到对应的连接信息
        Object o = redisUtils.hGet(key, field);

        //如果不为空，则将session的连接状态更新为下线状态
        if(o != null) {
            MemberSession memberSession = JSONUtil.toBean((String) o, MemberSession.class);
            memberSession.setConnectStatus(ConnectStatusEnum.OFFLINE.getCode());
            redisUtils.hPut(key, field, JSONUtil.toJsonStr(memberSession));
        }

        ctx.channel().close();
    }
}
