package com.whoiszxl.taowu.strategy.impl;

import com.whoiszxl.taowu.cqrs.command.LikeCommand;
import com.whoiszxl.taowu.enums.LikeTypeEnum;
import com.whoiszxl.taowu.strategy.LikeStrategy;
import org.springframework.stereotype.Component;

/**
 * 评论点赞策略实现
 *
 * @author whoiszxl
 * @date 2021/12/8
 */
@Component
public class CommentLike extends LikeStrategy {

    @Override
    public void like(LikeCommand likeCommand) {
        likeByType(likeCommand, LikeTypeEnum.COMMENT);
    }

    @Override
    public Integer isLike(Long id, Long memberId) {
        return isLike(id, memberId, LikeTypeEnum.COMMENT);
    }

    @Override
    public Integer getLikeCount(Long id) {
        return getLikeCountByType(LikeTypeEnum.COMMENT, id);
    }

    @Override
    public String getLikeTypeName() {
        return LikeTypeEnum.COMMENT.getTypeName();
    }
}