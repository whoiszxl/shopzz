package com.whoiszxl.taowu.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 商品SKU库存表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-21
 */
@Data
@Schema(description = "商品SKU库存表")
public class SkuStockFeignDTO implements Serializable {

    @Schema(description = "主键")
    private Long id;

    @Schema(description = "商品sku ID")
    private Long skuId;

    @Schema(description = "销售库存")
    private Integer saleStockQuantity;

    @Schema(description = "锁定库存")
    private Integer lockedStockQuantity;

    @Schema(description = "已销售库存")
    private Integer saledStockQuantity;

    @Schema(description = "库存状态,0:无库存,1:有库存")
    private Integer stockStatus;

}
