package com.whoiszxl.cqrs.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.whoiszxl.bean.PageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 优惠券表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-29
 */
@Getter
@Setter
@ApiModel(value = "Coupon对象", description = "优惠券表")
public class CouponQuery extends PageQuery {

    @ApiModelProperty("优惠券名称")
    private String name;

    @ApiModelProperty("优惠券有效期起始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime startTime;

    @ApiModelProperty("优惠券有效期结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime endTime;

    @ApiModelProperty("优惠券类型 1: 满减券, 2: 满减折扣券 3: 无门槛立减券")
    private Integer type;

}
