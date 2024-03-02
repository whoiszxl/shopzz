package com.whoiszxl.taowu.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 商品三级分类表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-21
 */
@Data
@TableName("pms_category")
@Schema(description = "商品三级分类表")
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "分类id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "分类名称")
    private String name;

    @Schema(description = "父类目的主键")
    private Long parentId;

    @Schema(description = "分类级别:1->1级; 2->2级 3->3级")
    private Integer level;

    @Schema(description = "是否显示[0-不显示,1-显示]")
    private Integer status;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "图标地址")
    private String icon;

    @Schema(description = "乐观锁")
    @Version
    private Long version;

    @Schema(description = "逻辑删除 1: 已删除, 0: 未删除")
    @TableLogic
    private Integer isDeleted;

    @Schema(description = "创建者")
    private String createdBy;

    @Schema(description = "更新者")
    private String updatedBy;

    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createdAt;

    @Schema(description = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updatedAt;


}
