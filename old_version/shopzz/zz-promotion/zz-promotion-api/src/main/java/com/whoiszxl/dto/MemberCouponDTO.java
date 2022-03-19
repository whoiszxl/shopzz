package com.whoiszxl.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="用户领取优惠券列表对象", description="用户领取优惠券列表对象")
public class MemberCouponDTO implements Serializable {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "优惠券ID")
    private Long couponId;

    @ApiModelProperty(value = "用户账号ID")
    private Long memberId;

    @ApiModelProperty(value = "是否使用过这个优惠券，1：使用了，0：未使用")
    private Integer isUsed;

    @ApiModelProperty(value = "使用优惠券的时间")
    private Date usedTime;

    @ApiModelProperty(value = "优惠券名称")
    private String name;

    @ApiModelProperty(value = "优惠券类型")
    private Integer type;

    @ApiModelProperty(value = "优惠券规则")
    private String rule;

    @ApiModelProperty(value = "开始时间")
    private Date startTime;

    @ApiModelProperty(value = "结束时间")
    private Date endTime;
}
