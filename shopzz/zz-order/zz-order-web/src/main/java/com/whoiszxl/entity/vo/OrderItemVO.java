package com.whoiszxl.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 订单明细表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-01-09
 */
@Data
@ApiModel(value = "OrderItem对象", description = "订单明细表")
public class OrderItemVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
      private Long id;

    @ApiModelProperty("订单ID")
    private Long orderId;

    @ApiModelProperty("订单编号")
    private String orderSn;

    @ApiModelProperty("商品id")
    private Long productId;

    @ApiModelProperty("分类id")
    private Long categoryId;

    @ApiModelProperty("sku id")
    private Long skuId;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("sku图片地址")
    private String skuPic;

    @ApiModelProperty("sku价格")
    private BigDecimal skuPrice;

    @ApiModelProperty("购买数量")
    private Integer quantity;

    @ApiModelProperty("商品销售属性")
    private String skuAttrs;

    @ApiModelProperty("促销活动ID")
    private Long promotionActivityId;

    @ApiModelProperty("商品促销分解金额")
    private BigDecimal promotionAmount;

    @ApiModelProperty("优惠券优惠分解金额")
    private BigDecimal couponAmount;

    @ApiModelProperty("积分优惠分解金额")
    private BigDecimal pointAmount;

    @ApiModelProperty("该商品经过优惠后的分解金额")
    private BigDecimal realAmount;


}
