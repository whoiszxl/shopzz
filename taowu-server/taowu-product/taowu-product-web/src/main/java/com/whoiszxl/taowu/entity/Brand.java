package com.whoiszxl.taowu.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 品牌表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-21
 */
@Data
@TableName("pms_brand")
@Schema(description = "品牌表")
public class Brand implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "品牌主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "品牌中文名")
    private String chineseName;

    @Schema(description = "品牌英文名")
    private String englishName;

    @Schema(description = "品牌别名")
    private String aliasName;

    @Schema(description = "首字母")
    private String firstLetter;

    @Schema(description = "品牌logo地址")
    private String logo;

    @Schema(description = "品牌介绍")
    private String description;

    @Schema(description = "品牌授权图片")
    private String authPic;

    @Schema(description = "品牌所在地")
    private String location;

    @Schema(description = "显示状态[0-不显示; 1-显示]")
    private Integer showStatus;

    @Schema(description = "品牌说明备注")
    private String remark;

    @Schema(description = "排序")
    private Integer sort;

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
    private LocalDateTime createdAt;

    @Schema(description = "更新时间")
    private LocalDateTime updatedAt;


}
