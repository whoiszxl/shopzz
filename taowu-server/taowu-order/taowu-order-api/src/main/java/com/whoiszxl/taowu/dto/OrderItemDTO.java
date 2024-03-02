package com.whoiszxl.taowu.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 订单明细表
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "订单明细表")
public class OrderItemDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
    private Long id;

    @Schema(description = "订单ID")
    private Long orderId;

    @Schema(description = "订单编号")
    private String orderSn;

    @Schema(description = "商品id")
    private Long productId;

    @Schema(description = "分类id")
    private Long categoryId;

    @Schema(description = "sku id")
    private Long skuId;

    @Schema(description = "sku名称")
    private String skuName;

    @Schema(description = "sku图片地址")
    private String skuPic;

    @Schema(description = "sku价格")
    private BigDecimal skuPrice;

    @Schema(description = "购买数量")
    private Integer quantity;

    @Schema(description = "商品销售属性")
    private String skuAttrs;

    @Schema(description = "促销活动ID")
    private Long promotionActivityId;

    @Schema(description = "商品促销分解金额")
    private BigDecimal promotionAmount;

    @Schema(description = "优惠券优惠分解金额")
    private BigDecimal couponAmount;

    @Schema(description = "积分优惠分解金额")
    private BigDecimal pointAmount;

    @Schema(description = "该商品经过优惠后的分解金额")
    private BigDecimal realAmount;

    @Schema(description = "乐观锁")
    private Long version;

    @Schema(description = "逻辑删除 1: 已删除， 0: 未删除")
    private Integer isDeleted;

    @Schema(description = "创建者")
    private String createdBy;

    @Schema(description = "更新者")
    private String updatedBy;

    @Schema(description = "创建时间")
    private LocalDateTime createdAt;

    @Schema(description = "更新时间")
    private LocalDateTime updatedAt;
}
