package com.whoiszxl.taowu.cqrs.command;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Schema(description = "优惠券表")
@JsonIgnoreProperties(ignoreUnknown = true)
public class CouponSaveCommand implements Serializable {


    @Schema(description = "优惠券名称")
    private String name;

    @Schema(description = "优惠券副名称")
    private String subName;

    @Schema(description = "优惠券有效期起始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime startTime;

    @Schema(description = "优惠券有效期结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
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

}
