package com.whoiszxl.taowu.cqrs.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Schema(description = "我的优惠券列表返回实体")
public class MyCouponApiResponse implements Serializable {

    @Schema(description = "自增主键ID")
    private Long id;

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

    @Schema(description = "优惠券状态: 1-未使用 2-已使用 3-已过期")
    private Integer status;

    @Schema(description = "优惠券的领取时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime getTime;

    @Schema(description = "优惠券的使用时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime useTime;

}
