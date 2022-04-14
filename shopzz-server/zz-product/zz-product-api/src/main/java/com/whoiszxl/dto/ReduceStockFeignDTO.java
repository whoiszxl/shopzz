package com.whoiszxl.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 订单明细表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-04-09
 */
@Getter
@Setter
@ApiModel(value = "库存扣减feign dto", description = "库存扣减feign dto")
public class ReduceStockFeignDTO implements Serializable {

    @ApiModelProperty("sku id")
    private Long skuId;

    @ApiModelProperty("扣减数量")
    private Integer quantity;

}
