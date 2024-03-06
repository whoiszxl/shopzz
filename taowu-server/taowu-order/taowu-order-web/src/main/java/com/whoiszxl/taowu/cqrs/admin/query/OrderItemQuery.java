package com.whoiszxl.taowu.cqrs.admin.query;

import com.whoiszxl.taowu.common.annotation.Query;
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
@Schema(description = "订单明细查询条件")
public class OrderItemQuery implements Serializable {


    @Query
    @Schema(description = "主键")
    private Long id;

    @Query
    @Schema(description = "订单ID")
    private Long orderId;

    @Query
    @Schema(description = "订单编号")
    private String orderNo;

    @Query
    @Schema(description = "商品id")
    private Long productId;

}
