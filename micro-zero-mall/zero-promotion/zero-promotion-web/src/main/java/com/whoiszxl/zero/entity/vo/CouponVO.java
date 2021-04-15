package com.whoiszxl.zero.entity.vo;

import com.whoiszxl.zero.bean.AbstractObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 优惠券表(PromotionCoupon)实体类
 *
 * @author whoiszxl
 * @since 2021-04-09
 */
@Data
public class CouponVO extends AbstractObject implements Serializable {
    private static final long serialVersionUID = 142764991472035765L;

    @ApiModelProperty(value = "主键ID")
    private Long id;

    @ApiModelProperty(value = "优惠券名称")
    private String name;

    @ApiModelProperty(value = "优惠券类型，1：现金券，2：满减券")
    private Integer type;

    @ApiModelProperty(value = "优惠券规则")
    private String rule;

    @ApiModelProperty(value = "有效期开始时间")
    private Date startTime;

    @ApiModelProperty(value = "有效期结束时间")
    private Date endTime;

    @ApiModelProperty(value = "优惠券总量")
    private Long allCount;

    @ApiModelProperty(value = "优惠券已经领取的数量")
    private Long receivedCount;

    @ApiModelProperty(value = "优惠券发放方式，1：可发放可领取，2：仅可发放，3：仅可领取")
    private Integer giveType;

    @ApiModelProperty(value = "优惠券状态，1：未开始；2：发放中，3：已发完；4：已过期")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private Date createdAt;

    @ApiModelProperty(value = "更新时间")
    private Date updatedAt;

}