package com.whoiszxl.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 商品SKU库存表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-21
 */
@Getter
@Setter
@ApiModel(value = "SkuStock对象", description = "商品SKU库存表")
public class SkuStockFeignDTO implements Serializable {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("商品sku ID")
    private Long skuId;

    @ApiModelProperty("销售库存")
    private Integer saleStockQuantity;

    @ApiModelProperty("锁定库存")
    private Integer lockedStockQuantity;

    @ApiModelProperty("已销售库存")
    private Integer saledStockQuantity;

    @ApiModelProperty("库存状态,0:无库存,1:有库存")
    private Integer stockStatus;

}
