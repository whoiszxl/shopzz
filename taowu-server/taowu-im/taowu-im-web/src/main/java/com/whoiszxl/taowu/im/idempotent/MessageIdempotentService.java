package com.whoiszxl.taowu.im.idempotent;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.whoiszxl.taowu.im.pack.GroupChatPack;
import com.whoiszxl.taowu.im.pack.PrivateChatPack;
import com.whoiszxl.zhipin.tools.common.utils.RedisUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class MessageIdempotentService {

    private final RedisUtils redisUtils;

    public static final String PRIVATE_CHAT_KEY_PREFIX = "private-chat-message-cache";
    public static final String GROUP_CHAT_KEY_PREFIX = "group-chat-message-cache";

    public void setPrivateChatMessageCache(PrivateChatPack privateChatPack) {
        String key = String.format("%s:%s-%s", PRIVATE_CHAT_KEY_PREFIX, privateChatPack.getFromMemberId(), privateChatPack.getMessageId());
        redisUtils.setEx(key, JSONUtil.toJsonStr(privateChatPack), 10, TimeUnit.SECONDS);
    }

    public PrivateChatPack getPrivateChatMessageCache(PrivateChatPack privateChatPack) {
        String key = String.format("%s:%s-%s", PRIVATE_CHAT_KEY_PREFIX, privateChatPack.getFromMemberId(), privateChatPack.getMessageId());
        String json = redisUtils.get(key);
        if(StrUtil.isBlank(json)) {
            return null;
        }
        return JSONUtil.toBean(json, PrivateChatPack.class);
    }


    public void setGroupChatMessageCache(GroupChatPack groupChatPack) {
        String key = String.format("%s:%s:%s:%s", GROUP_CHAT_KEY_PREFIX, groupChatPack.getGroupId(), groupChatPack.getFromMemberId(), groupChatPack.getMessageId());
        redisUtils.setEx(key, JSONUtil.toJsonStr(groupChatPack), 10, TimeUnit.MINUTES);
    }

    public GroupChatPack getGroupChatMessageCache(GroupChatPack groupChatPack) {
        String key = String.format("%s:%s:%s:%s", GROUP_CHAT_KEY_PREFIX, groupChatPack.getGroupId(), groupChatPack.getFromMemberId(), groupChatPack.getMessageId());
        String json = redisUtils.get(key);
        if(StrUtil.isBlank(json)) {
            return null;
        }
        return JSONUtil.toBean(json, GroupChatPack.class);
    }
}
