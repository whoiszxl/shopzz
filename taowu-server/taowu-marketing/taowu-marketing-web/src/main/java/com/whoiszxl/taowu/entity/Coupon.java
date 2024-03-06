package com.whoiszxl.taowu.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 优惠券表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-29
 */
@Data
@TableName("spms_coupon")
@Schema(description = "优惠券表")
public class Coupon implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "自增主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "优惠券名称")
    private String name;

    @Schema(description = "优惠券副名称")
    private String subName;

    @Schema(description = "优惠券有效期起始时间")
    private LocalDateTime startTime;

    @Schema(description = "优惠券有效期结束时间")
    private LocalDateTime endTime;

    @Schema(description = "优惠券使用阈值")
    private BigDecimal useThreshold;

    @Schema(description = "折扣金额")
    private BigDecimal discountAmount;

    @Schema(description = "折扣比率")
    private BigDecimal discountRate;

    @Schema(description = "优惠券类型 1: 满减券, 2: 满减折扣券 3: 无门槛立减券")
    private Integer type;

    @Schema(description = "是否有全场限制 1: 全场通用, 2: 分类限制")
    private Integer fullLimited;

    @Schema(description = "优惠券状态 1: 有效, 2: 失效(超出有效期), 3: 系统停用")
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
