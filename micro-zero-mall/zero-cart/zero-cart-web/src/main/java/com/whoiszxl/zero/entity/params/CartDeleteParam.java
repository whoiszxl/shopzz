package com.whoiszxl.zero.entity.params;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * TODO
 *
 * @author zhouxiaolong
 * @date 2021/4/19
 */
@Data
public class CartDeleteParam {

    @ApiModelProperty("购物车商品SKU ID")
    private Long id;
}
