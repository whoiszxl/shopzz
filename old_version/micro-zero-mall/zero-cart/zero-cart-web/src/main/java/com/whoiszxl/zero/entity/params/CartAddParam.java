package com.whoiszxl.zero.entity.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * TODO
 *
 * @author zhouxiaolong
 * @date 2021/4/19
 */
@ApiModel("添加购物车参数")
@Data
public class CartAddParam {

    @ApiModelProperty("sku id")
    private Long skuId;

    @ApiModelProperty("数量")
    private Integer num;
}
