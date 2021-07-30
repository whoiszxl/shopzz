package com.whoiszxl.entity.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("购物车更新数量的参数")
public class CartUpdateQuantityQuery {

    @ApiModelProperty("购物车ID")
    private Long cartId;

    @ApiModelProperty("更新数量")
    private Integer updateQuantity;
}
