package com.whoiszxl.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("sku 库存 dto")
public class InventorySkuDTO {

    @ApiModelProperty("sku id")
    private Long skuId;

    @ApiModelProperty("库存数量")
    private Integer quantity;
}
