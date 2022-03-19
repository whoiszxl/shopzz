package com.whoiszxl.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

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
@ApiModel(value="OrderItem对象", description="订单明细表")
public class OrderItemDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "订单ID")
    private Long orderId;

    @ApiModelProperty(value = "订单编号")
    private String orderSn;

    @ApiModelProperty(value = "商品id")
    private Long productId;

    @ApiModelProperty(value = "分类id")
    private Long categoryId;

    @ApiModelProperty(value = "sku id")
    private Long skuId;

    @ApiModelProperty(value = "sku名称")
    private String skuName;

    @ApiModelProperty(value = "sku图片地址")
    private String skuPic;

    @ApiModelProperty(value = "sku价格")
    private BigDecimal skuPrice;

    @ApiModelProperty(value = "购买数量")
    private Integer quantity;

    @ApiModelProperty(value = "商品销售属性")
    private String skuAttrs;

    @ApiModelProperty(value = "促销活动ID")
    private Long promotionActivityId;

    @ApiModelProperty(value = "商品促销分解金额")
    private BigDecimal promotionAmount;

    @ApiModelProperty(value = "优惠券优惠分解金额")
    private BigDecimal couponAmount;

    @ApiModelProperty(value = "积分优惠分解金额")
    private BigDecimal pointAmount;

    @ApiModelProperty(value = "该商品经过优惠后的分解金额")
    private BigDecimal realAmount;

}
