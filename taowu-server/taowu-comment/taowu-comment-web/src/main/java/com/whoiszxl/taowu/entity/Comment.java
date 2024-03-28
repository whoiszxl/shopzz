package com.whoiszxl.taowu.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

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
@Getter
@Setter
@TableName("cmt_comment")
@Schema(description = "评论主表")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
    @TableId(value = "id", type = IdType.AUTO)
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

    @Schema(description = "客户端IP地址")
    private String clientIp;

    @Schema(description = "客户端IP地址所属地")
    private String ipAddress;

    @Schema(description = "乐观锁")
    @Version
    private Long version;

    @Schema(description = "业务状态")
    private Integer status;

    @Schema(description = "逻辑删除 1: 已删除， 0: 未删除")
    @TableLogic
    private Integer isDeleted;

    @Schema(description = "创建者")
    private String createdBy;

    @Schema(description = "更新者")
    private String updatedBy;

    @Schema(description = "创建时间")
    private LocalDateTime createdAt;

    @Schema(description = "更新时间")
    private LocalDateTime updatedAt;


}
