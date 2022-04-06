package com.whoiszxl.cqrs.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "SKU返回实体")
public class SkuStockVO {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("商品sku ID")
    private Long skuId;

    @ApiModelProperty("销售库存")
    private Integer saleStockQuantity;

    @ApiModelProperty("库存状态,0:无库存,1:有库存")
    private Integer stockStatus;
}

