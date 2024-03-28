package com.whoiszxl.taowu.cqrs.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "评论发布命令")
public class CommentPublishCommand {

    @Schema(description = "所属模块: 1-视频 2-商品")
    private Integer module;

    @Schema(description = "对象ID: 视频 || 商品")
    private Long objId;

    @Schema(description = "父评论ID，为 0 则是根评论")
    private Long parentId;

    @Schema(description = "回复评论ID，为 0 则是回复根评论的")
    private Long replyId;

    @Schema(description = "评论内容")
    private String content;

    @Schema(description = "评论图片，多个图片以英文逗号分隔")
    private String picList;

    @Schema(description = "客户端IP地址")
    private String clientIp;

}
