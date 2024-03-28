package com.whoiszxl.taowu.cqrs.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 评论主表
 * </p>
 *
 * @author whoiszxl
 * @since 2024-03-26
 */
@Data
@Schema(description = "评论主表")
public class CommentResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
    private Long id;

    @Schema(description = "所属模块: 1-视频 2-商品")
    private Integer module;

    @Schema(description = "对象ID: 视频 || 商品")
    private Long objId;

    @Schema(description = "父评论ID，为 0 则是根评论")
    private Long parentId;

    @Schema(description = "回复评论ID，为 0 则是回复根评论的")
    private Long replyId;

    @Schema(description = "用户ID")
    private Long memberId;

    @Schema(description = "用户头像")
    private String avatar;

    @Schema(description = "用户名称")
    private String username;

    @Schema(description = "评论内容")
    private String content;

    @Schema(description = "模糊计数的点赞数量")
    private Integer upCount;

    @Schema(description = "模糊计数的点踩数量")
    private Integer downCount;

    @Schema(description = "评论图片，多个图片以英文逗号分隔")
    private String picList;

    @Schema(description = "热门状态: 0-非热门 1-是热门")
    private Integer hotStatus;

    @Schema(description = "置顶状态: 0-未置顶 1-已置顶")
    private Integer topStatus;

    @Schema(description = "子评论数")
    private Integer childCommentCount;

    @Schema(description = "客户端IP地址所属地")
    private String ipAddress;

    @Schema(description = "创建时间")
    private LocalDateTime createdAt;

}
