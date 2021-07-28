package com.whoiszxl.entity.vo;

import com.whoiszxl.bean.AbstractObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 优惠券表
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Coupon对象", description="优惠券表")
public class CouponVO extends AbstractObject implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
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

    @ApiModelProperty(value = "创建者")
    private String createdBy;

    @ApiModelProperty(value = "更新时间")
    private Date updatedAt;

    @ApiModelProperty(value = "乐观锁")
    private Long version;

    @ApiModelProperty(value = "逻辑删除 1: 已删除， 0: 未删除")
    private Integer isDeleted;

    @ApiModelProperty(value = "更新者")
    private String updatedBy;


}
