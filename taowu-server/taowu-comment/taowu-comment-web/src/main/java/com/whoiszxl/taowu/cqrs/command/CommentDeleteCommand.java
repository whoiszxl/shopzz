package com.whoiszxl.taowu.cqrs.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "评论删除命令")
public class CommentDeleteCommand {

    @Schema(description = "所属模块: 1-视频 2-商品")
    private Integer module;

    @Schema(description = "对象ID: 视频 || 商品")
    private Long objId;

    @Schema(description = "评论ID")
    private Long commentId;

}
