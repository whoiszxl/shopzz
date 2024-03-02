package com.whoiszxl.taowu.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.whoiszxl.taowu.common.constants.RedisPrefixConstants;
import com.whoiszxl.taowu.common.exception.ExceptionCatcher;
import com.whoiszxl.taowu.common.token.TokenHelper;
import com.whoiszxl.taowu.common.utils.RedisUtils;
import com.whoiszxl.taowu.cqrs.command.CounterMemberCommand;
import com.whoiszxl.taowu.cqrs.command.CounterVideoCommand;
import com.whoiszxl.taowu.enums.CounterVideoTypeEnum;
import com.whoiszxl.taowu.service.IBlurryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 模糊计数服务实现
 * @author whoiszxl
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BlurryServiceImpl implements IBlurryService {

    private final RedisUtils redisUtils;

    private final TokenHelper tokenHelper;

    @Override
    public void memberBlurry(CounterMemberCommand command) {
        //1. 从Redis中获取member的计数kv键值对
        String redisKey = RedisPrefixConstants.format(
                RedisPrefixConstants.Counter.COUNTER_MEMBER_HASH_KEY,
                command.getMemberId().toString());

        //2. 通过 hash 进行计数
        redisUtils.hIncrBy(redisKey, CounterVideoTypeEnum.getNameByCode(command.getType()), command.getOperator());

        //3. todo 定期更新到数据库中
    }

    @Override
    public void videoBlurry(CounterVideoCommand command) {
        Long memberId = tokenHelper.getAppMemberId();
        //1. 从Redis中获取member的计数kv键值对
        String redisKey = RedisPrefixConstants.format(
                RedisPrefixConstants.Counter.COUNTER_VIDEO_HASH_KEY,
                command.getVideoId().toString());
        String redisSetKey = redisKey + ":set";

        if(command.getOperator() > 0) {
            //2. 判断是否是点赞，需要记录点赞用户的ID
            if(ObjectUtil.equals(command.getType(), CounterVideoTypeEnum.LIKE_COUNT.getType())) {
                //2.1 判断是否已经点赞了
                Boolean isMember = redisUtils.sIsMember(redisSetKey, memberId.toString());
                if(isMember) {
                    ExceptionCatcher.catchServiceEx("已经点赞了");
                }
                redisUtils.sAdd(redisSetKey, memberId.toString());
            }
        }else {
            redisUtils.sRemove(redisSetKey, memberId.toString());
        }

        //3. 通过 hash 进行计数
        redisUtils.hIncrBy(redisKey, CounterVideoTypeEnum.getNameByCode(command.getType()), command.getOperator());

        //4. todo 定期更新到数据库中或者通过MQ异步保存到数据库中
    }
}
