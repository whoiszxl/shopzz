package com.whoiszxl.cqrs.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="PurchaseOrderItem对象", description="采购订单商品详情表")
public class PurchaseOrderItemVO implements Serializable {

    @ApiModelProperty("采购单ID")
    private Long purchaseOrderId;

    @ApiModelProperty("商品SKU ID")
    private Long skuId;

    @ApiModelProperty("采购数量")
    private Long purchaseQuantity;

    @ApiModelProperty("采购价格")
    private BigDecimal purchasePrice;

    @ApiModelProperty("合格商品的数量")
    private Integer qualifiedCount;

    @ApiModelProperty("到货的商品数量")
    private Integer arrivalCount;

    @ApiModelProperty("仓库ID")
    private Long warehouseId;

    @ApiModelProperty("仓库货架ID")
    private Long warehouseShelfId;

}