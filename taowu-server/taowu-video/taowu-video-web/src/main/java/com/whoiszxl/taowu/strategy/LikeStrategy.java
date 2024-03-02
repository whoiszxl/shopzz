package com.whoiszxl.taowu.strategy;

import com.whoiszxl.taowu.common.constants.RedisPrefixConstants;
import com.whoiszxl.taowu.common.utils.RedisUtils;
import com.whoiszxl.taowu.cqrs.command.LikeCommand;
import com.whoiszxl.taowu.enums.LikeTypeEnum;
import com.whoiszxl.taowu.enums.VideoCounterStatusEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 点赞策略
 *
 * @author whoiszxl
 * @date 2021/12/8
 */
@Component
public abstract class LikeStrategy {

    @Autowired
    protected RedisUtils redisUtils;

    /**
     * 通过点赞类型进行点赞操作
     * @param likeCommand
     * @param likeTypeEnum
     */
    protected void likeByType(LikeCommand likeCommand, LikeTypeEnum likeTypeEnum) {
        //video:video_like:
        String redisKey = RedisPrefixConstants.Video.VIDEO_PREFIX + likeTypeEnum.getTypeName() + ":" + likeCommand.getId();
        String countKey = redisKey + ":count";
        String setKey = redisKey + ":set";

        //如果是新增,添加一条记录到hash里
        if(VideoCounterStatusEnum.INCR.getCode().equals(likeCommand.getStatus())) {
            if(Boolean.FALSE.equals(redisUtils.sIsMember(setKey,likeCommand.getMemberId() + ""))) {
                redisUtils.sAdd(setKey, likeCommand.getMemberId() + "");
                redisUtils.incrBy(countKey, 1);
            }
        }else {
            if(Boolean.TRUE.equals(redisUtils.sIsMember(setKey, likeCommand.getMemberId() + ""))) {
                redisUtils.incrBy(countKey, -1);
                redisUtils.sRemove(setKey, likeCommand.getMemberId() + "");
                if(redisUtils.sSize(setKey) == 0) {
                    redisUtils.delete(setKey);
                }
            }
        }
    }

    protected Integer getLikeCountByType(LikeTypeEnum likeTypeEnum, Long id) {
        String redisKey = RedisPrefixConstants.Video.VIDEO_PREFIX + likeTypeEnum.getTypeName() + ":" + id;
        String countKey = redisKey + ":count";
        String count = redisUtils.get(countKey);
        return StringUtils.isBlank(count) ? 0 : Integer.parseInt(count);
    }

    /**
     * 判断用户是否对此记录进行过点赞
     * @param id 记录ID
     * @param memberId 会员ID
     * @param likeTypeEnum 点赞记录的类型
     * @return 是否点过赞
     */
    protected Integer isLike(Long id, Long memberId, LikeTypeEnum likeTypeEnum) {
        String redisKey = RedisPrefixConstants.Video.VIDEO_PREFIX + likeTypeEnum.getTypeName() + ":" + id + ":set";
        return redisUtils.sIsMember(redisKey, memberId + "") ? 1 : 0;
    }

    /**
     * 通过like类型和记录ID获取到记录的点赞数
     * @param id 记录ID
     * @return
     */
    public abstract Integer getLikeCount(Long id);

    public abstract void like(LikeCommand likeCommand);

    public abstract Integer isLike(Long id, Long memberId);

    public abstract String getLikeTypeName();

}
