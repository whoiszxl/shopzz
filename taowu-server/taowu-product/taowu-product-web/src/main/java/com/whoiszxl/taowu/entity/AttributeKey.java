package com.whoiszxl.taowu.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 属性键表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-21
 */
@Data
@TableName("pms_attribute_key")
@Schema(description = "属性键表")
public class AttributeKey implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "属性key主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "属性名称")
    private String name;

    @Schema(description = "属性单位")
    private String unit;

    @Schema(description = "是否为标准属性")
    private Integer standard;

    @Schema(description = "属性类型[0-销售属性,1-基本属性]")
    private Integer type;

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
