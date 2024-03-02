package com.whoiszxl.taowu.cqrs.command;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 点赞命令
 *
 * @author whoiszxl
 * @date 2022/1/27
 */
@Data
@Schema(description = "点赞命令")
public class LikeCommand {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(description = "点赞id，视频id or 评论id")
    private Long id;

    @Schema(description = "点赞id： 1:视频点赞 2:评论点赞")
    private Integer likeType;

    @Schema(description = "状态：1: 增加 2: 减少")
    private Integer status;

    @Schema(description = "用户ID")
    private Long memberId;

}