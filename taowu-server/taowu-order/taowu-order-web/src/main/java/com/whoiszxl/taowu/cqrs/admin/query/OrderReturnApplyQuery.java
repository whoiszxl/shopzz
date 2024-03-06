package com.whoiszxl.taowu.cqrs.admin.query;

import com.whoiszxl.taowu.common.annotation.Query;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 订单退货表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-04-09
 */
@Data
@Schema(description = "订单退货表")
public class OrderReturnApplyQuery implements Serializable {

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
    @Schema(description = "用户名")
    private String username;


}
