package com.whoiszxl.taowu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 优惠券跟分类的关联关系表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-29
 */
@Data
@TableName("spms_coupon_category")
@Schema(description = "优惠券跟分类的关联关系表")
public class CouponCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "自增主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "优惠券ID")
    private Long couponId;

    @Schema(description = "一级分类ID (为0则为全场通用)")
    private Long categoryId;

    @Schema(description = "二级分类ID")
    private Long parentCategoryId;


}
