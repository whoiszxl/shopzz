package com.whoiszxl.taowu.im.sequence;

import com.whoiszxl.taowu.common.utils.RedisUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisSequenceServiceImpl implements SequenceService{

    private final RedisUtils redisUtils;

    @Override
    public Long getPrivateChatSequenceId(String key, Long fromMemberId, Long toMemberId) {
        String finalKey;
        if(fromMemberId > toMemberId) {
            finalKey = key + ":" + fromMemberId + "-" + toMemberId;
        }else {
            finalKey = key + ":" + toMemberId + "-" + fromMemberId;
        }

        return redisUtils.incrBy(finalKey, 1);
    }

    @Override
    public Long getGroupChatSequenceId(String key, Long groupId) {
        return redisUtils.incrBy(String.format("%s:%s", key, groupId), 1);
    }
}
