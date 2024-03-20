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
 * 计数表
 * </p>
 *
 * @author whoiszxl
 * @since 2024-03-19
 */
@Getter
@Setter
@TableName("common_counter")
@Schema(description = "计数表")
public class Counter implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID")
    private Long id;

    @Schema(description = "维度类型: 1-用户维度 2-内容维度 3-标签维度 4-评论维度")
    private Integer dimType;

    @Schema(description = "对象类型: 10-发布内容数 11-被点赞数 12-被收藏数 13-关注数 14-粉丝数 15-点赞内容数 16-收藏内容数 20-内容点赞数 21-内容阅读数 22-内容分享数 23-内容收藏数 24-内容评论数 30-话题内容数 31-特效内容数 32-商品内容数 33-品牌内容数 40-评论点赞数")
    private Integer objType;

    @Schema(description = "对象ID: 用户ID || 视频ID || 评论ID || 其他ID")
    private Long objId;

    @Schema(description = "计数值")
    private Long countValue;

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
