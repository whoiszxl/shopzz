package com.whoiszxl.zero.entity.vo;

import com.whoiszxl.zero.bean.AbstractObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 优惠券领取记录表(PromotionCouponReceivedRecord)实体类
 *
 * @author whoiszxl
 * @since 2021-04-09
 */
@Data
public class CouponReceivedRecordVO extends AbstractObject implements Serializable {
    private static final long serialVersionUID = 370678469786864280L;

    @ApiModelProperty(value = "主键ID")
    private Long id;

    @ApiModelProperty(value = "优惠券ID")
    private Long couponId;

    @ApiModelProperty(value = "用户账号ID")
    private Long memberId;

    @ApiModelProperty(value = "是否使用过这个优惠券，1：使用了，0：未使用")
    private Integer isUsed;

    @ApiModelProperty(value = "使用优惠券的时间")
    private Date usedTime;

    @ApiModelProperty(value = "创建时间")
    private Date createdAt;

    @ApiModelProperty(value = "更新时间")
    private Date updatedAt;

}