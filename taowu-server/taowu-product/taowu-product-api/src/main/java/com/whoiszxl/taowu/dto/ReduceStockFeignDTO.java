package com.whoiszxl.taowu.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 订单明细表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-04-09
 */
@Data
@Schema(description = "库存扣减feign dto")
public class ReduceStockFeignDTO implements Serializable {

    @Schema(description = "sku id")
    private Long skuId;

    @Schema(description = "扣减数量")
    private Integer quantity;

}
