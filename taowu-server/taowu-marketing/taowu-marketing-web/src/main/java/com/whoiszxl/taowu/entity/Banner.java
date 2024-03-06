package com.whoiszxl.taowu.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 轮播表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-29
 */
@Data
@TableName("spms_banner")
@Schema(description = "轮播表")
public class Banner implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "自增主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "轮播图名称")
    private String name;

    @Schema(description = "轮播位置:0->PC首页轮播;1->app首页轮播 2->app导航小组件")
    private Integer type;

    @Schema(description = "业务ID: spu_id or other")
    private Long bizId;

    @Schema(description = "图片地址")
    private String pic;

    @Schema(description = "上下线状态:0->下线;1->上线")
    private Integer status;

    @Schema(description = "点击数")
    private Integer clickCount;

    @Schema(description = "链接地址")
    private String url;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "乐观锁")
    @Version
    private Long version;

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
