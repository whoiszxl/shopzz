package com.whoiszxl.taowu.cqrs.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "SKU返回实体")
public class SkuStockVO {

    @Schema(description = "主键")
    private Long id;

    @Schema(description = "商品sku ID")
    private Long skuId;

    @Schema(description = "销售库存")
    private Integer saleStockQuantity;

    @Schema(description = "库存状态,0:无库存,1:有库存")
    private Integer stockStatus;
}

