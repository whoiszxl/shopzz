package com.whoiszxl.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 优惠券跟分类的关联关系表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-29
 */
@Getter
@Setter
@TableName("spms_coupon_category")
@ApiModel(value = "CouponCategory对象", description = "优惠券跟分类的关联关系表")
public class CouponCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("自增主键ID")
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("优惠券ID")
    private Long couponId;

    @ApiModelProperty("一级分类ID (为0则为全场通用)")
    private Long categoryId;

    @ApiModelProperty("二级分类ID")
    private Long parentCategoryId;


}
