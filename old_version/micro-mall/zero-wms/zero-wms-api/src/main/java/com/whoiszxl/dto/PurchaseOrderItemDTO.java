package com.whoiszxl.dto;

import com.whoiszxl.bean.AbstractObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 采购订单商品详情表
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="PurchaseOrderItem对象", description="采购订单商品详情表")
public class PurchaseOrderItemDTO extends AbstractObject implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "采购单ID")
    private Long purchaseOrderId;

    @ApiModelProperty(value = "商品SKU ID")
    private Long productSkuId;

    @ApiModelProperty(value = "采购数量")
    private Integer purchaseQuantity;

    @ApiModelProperty(value = "采购价格")
    private BigDecimal purchasePrice;

    @ApiModelProperty(value = "合格商品的数量")
    private Integer qualifiedCount;

    @ApiModelProperty(value = "到货的商品数量")
    private Integer arrivalCount;

    @ApiModelProperty(value = "上架条目集合")
    List<PurchaseInboundOnItemDTO> onItems;

}
