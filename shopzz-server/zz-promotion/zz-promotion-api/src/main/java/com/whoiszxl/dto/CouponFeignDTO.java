package com.whoiszxl.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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
@ApiModel(value = "Coupon feign对象", description = "优惠券表")
public class CouponFeignDTO implements Serializable {

    @ApiModelProperty("优惠券可使用的分类ID集合")
    private List<Long> categoryIdList;

    @ApiModelProperty("自增主键ID")
    private Long id;

    @ApiModelProperty("优惠券名称")
    private String name;

    @ApiModelProperty("优惠券副名称")
    private String subName;

    @ApiModelProperty("优惠券有效期起始时间")
    private LocalDateTime startTime;

    @ApiModelProperty("优惠券有效期结束时间")
    private LocalDateTime endTime;

    @ApiModelProperty("优惠券使用阈值")
    private BigDecimal useThreshold;

    @ApiModelProperty("折扣金额")
    private BigDecimal discountAmount;

    @ApiModelProperty("折扣比率")
    private BigDecimal discountRate;

    @ApiModelProperty("优惠券类型 1: 满减券, 2: 满减折扣券 3: 无门槛立减券")
    private Integer type;

    @ApiModelProperty("是否有全场限制 1: 全场通用, 2: 分类限制")
    private Integer fullLimited;

    @ApiModelProperty("优惠券状态 1: 有效, 2: 失效(超出有效期), 3: 系统停用")
    private Integer status;

    @ApiModelProperty("乐观锁")
    private Long version;

    @ApiModelProperty("逻辑删除 1: 已删除, 0: 未删除")
    private Integer isDeleted;

    @ApiModelProperty("创建者")
    private String createdBy;

    @ApiModelProperty("更新者")
    private String updatedBy;

    @ApiModelProperty("创建时间")
    private LocalDateTime createdAt;

    @ApiModelProperty("更新时间")
    private LocalDateTime updatedAt;


}
