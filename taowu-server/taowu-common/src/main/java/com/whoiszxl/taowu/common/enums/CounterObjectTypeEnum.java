package com.whoiszxl.taowu.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CounterObjectTypeEnum {
    PUBLISH_CONTENT_COUNT(10, "发布内容数"),
    LIKE_COUNT(11, "被点赞数"),
    COLLECT_COUNT(12, "被收藏数"),
    FOLLOW_COUNT(13, "关注数"),
    FANS_COUNT(14, "粉丝数"),
    LIKE_CONTENT_COUNT(15, "点赞内容数"),
    COLLECT_CONTENT_COUNT(16, "收藏内容数"),
    CONTENT_LIKE_COUNT(20, "内容点赞数"),
    CONTENT_READ_COUNT(21, "内容阅读数"),
    CONTENT_SHARE_COUNT(22, "内容分享数"),
    CONTENT_COLLECT_COUNT(23, "内容收藏数"),
    CONTENT_COMMENT_COUNT(24, "内容评论数"),
    TOPIC_CONTENT_COUNT(30, "话题内容数"),
    EFFECT_CONTENT_COUNT(31, "特效内容数"),
    PRODUCT_CONTENT_COUNT(32, "商品内容数"),
    BRAND_CONTENT_COUNT(33, "品牌内容数"),
    COMMENT_LIKE_COUNT(40, "评论点赞数");

    private final Integer code;
    private final String desc;

}
