package com.whoiszxl.taowu.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 促销活动表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-29
 */
@Data
@TableName("spms_activity")
@Schema(description = "促销活动表")
public class Activity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "自增主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "促销活动名称")
    private String name;

    @Schema(description = "活动描述")
    private String descs;

    @Schema(description = "促销活动开始时间")
    private LocalDateTime startTime;

    @Schema(description = "促销活动结束时间")
    private LocalDateTime endTime;

    @Schema(description = "活动banner图")
    private String img;

    @Schema(description = "促销活动的状态,1:启用,2:停用")
    private Integer status;

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
