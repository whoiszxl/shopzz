package com.whoiszxl.taowu.cqrs.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 订单提交命令
 *
 * @author whoiszxl
 * @date 2022/1/12
 */
@Data
@Schema(description = "订单提交命令")
public class OrderSubmitCommand {

    @Schema(description = "地址ID")
    private Long addressId;

    @Schema(description = "订单token")
    private String orderToken;

    @Schema(description = "使用积分")
    private String usePoints;

    @Schema(description = "订单备注")
    private String orderComment;

    @Schema(description = "优惠券ID")
    private Long couponId;

    @Schema(description = "最终计算价格")
    private BigDecimal finalPrice;
}
