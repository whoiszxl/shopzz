package com.whoiszxl.entity.query;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("购物车更新数量的参数")
public class CartUpdateQuantityQuery {

    @ApiModelProperty("购物车ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private String cartId;

    @ApiModelProperty("更新数量")
    private Integer updateQuantity;
}
