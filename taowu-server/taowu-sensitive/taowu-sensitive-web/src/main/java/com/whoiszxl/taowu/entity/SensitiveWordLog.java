package com.whoiszxl.taowu.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 敏感词触发日志表
 * </p>
 *
 * @author whoiszxl
 * @since 2024-03-18
 */
@Getter
@Setter
@TableName("common_sensitive_word_log")
@Schema(description = "敏感词触发日志表")
public class SensitiveWordLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID")
    private Long id;

    @Schema(description = "用户ID")
    private Long memberId;

    @Schema(description = "视频ID")
    private Long videoId;

    @Schema(description = "原文")
    private String originalText;

    @Schema(description = "敏感词")
    private String sensitiveWord;

    @Schema(description = "原始视频")
    private String originalVideoUrl;

    @Schema(description = "原始视频")
    private String videoSensitiveReason;

    @Schema(description = "业务状态")
    private Integer status;

    @Schema(description = "乐观锁")
    @Version
    private Long version;

    @Schema(description = "逻辑删除 1: 已删除， 0: 未删除")
    @TableLogic
    private Integer isDeleted;

    @Schema(description = "创建时间")
    private LocalDateTime createdAt;

    @Schema(description = "更新时间")
    private LocalDateTime updatedAt;


}
