package com.whoiszxl.entity.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("购物车新增接口的参数")
public class CartAddQuery {

    @ApiModelProperty("商品sku id")
    private Long skuId;

    @ApiModelProperty("新增数量")
    private Integer quantity;
}
