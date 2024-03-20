package com.whoiszxl.taowu.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Schema(description = "计数命令")
public class CountFeignCommand {

    @Schema(description = "维度类型: 1-用户维度 2-内容维度 3-标签维度 4-评论维度")
    private Integer dimType;

    @Schema(description = "对象类型: 10-发布内容数 11-被点赞数 12-被收藏数 13-关注数 14-粉丝数 15-点赞内容数 16-收藏内容数 20-内容点赞数 21-内容阅读数 22-内容分享数 23-内容收藏数 24-内容评论数 30-话题内容数 31-特效内容数 32-商品内容数 33-品牌内容数 40-评论点赞数")
    private Integer objType;

    @Schema(description = "对象ID: 用户ID || 视频ID || 评论ID || 其他ID")
    private Long objId;

    @Schema(description = "计算规则: 1-增加 -1-减少")
    private Integer regulation;

}
