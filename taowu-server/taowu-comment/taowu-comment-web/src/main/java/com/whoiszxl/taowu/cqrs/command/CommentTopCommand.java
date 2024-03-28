package com.whoiszxl.taowu.cqrs.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "评论置顶命令")
public class CommentTopCommand {

    @Schema(description = "评论ID")
    private Long commentId;


}
