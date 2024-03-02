package com.whoiszxl.taowu.strategy.impl;

import com.whoiszxl.taowu.cqrs.command.LikeCommand;
import com.whoiszxl.taowu.enums.LikeTypeEnum;
import com.whoiszxl.taowu.strategy.LikeStrategy;
import org.springframework.stereotype.Component;

/**
 * 视频点赞策略实现
 *
 * @author whoiszxl
 * @date 2021/12/8
 */
@Component
public class VideoLike extends LikeStrategy {

    @Override
    public void like(LikeCommand likeCommand) {
        likeByType(likeCommand, LikeTypeEnum.VIDEO);
    }

    @Override
    public Integer isLike(Long id, Long memberId) {
        return isLike(id, memberId, LikeTypeEnum.VIDEO);
    }

    @Override
    public Integer getLikeCount(Long id) {
        return getLikeCountByType(LikeTypeEnum.VIDEO, id);
    }

    @Override
    public String getLikeTypeName() {
        return LikeTypeEnum.VIDEO.getTypeName();
    }
}
