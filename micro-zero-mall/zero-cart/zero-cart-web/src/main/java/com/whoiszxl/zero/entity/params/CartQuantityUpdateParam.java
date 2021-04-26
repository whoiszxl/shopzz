package com.whoiszxl.zero.entity.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("购物车数量更新请求参数")
public class CartQuantityUpdateParam {

    @ApiModelProperty("购物车主键ID")
    private Long id;

    @ApiModelProperty("需要修改的数量")
    private Integer num;
}
