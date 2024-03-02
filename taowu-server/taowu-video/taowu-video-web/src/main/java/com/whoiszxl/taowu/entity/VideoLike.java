package com.whoiszxl.taowu.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 点赞表
 * </p>
 *
 * @author whoiszxl
 * @since 2023-06-13
 */
@Data
@TableName("vms_video_like")
@Schema(description = "点赞表")
public class VideoLike implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID")
    private Long id;

    @Schema(description = "会员ID")
    private Long memberId;

    @Schema(description = "点赞类型: 1:短视频 2:评论")
    private Integer likeType;

    @Schema(description = "点赞类型相关记录的主键ID")
    private Long likeId;

    @Schema(description = "创建时间")
    private LocalDateTime createdAt;


}
