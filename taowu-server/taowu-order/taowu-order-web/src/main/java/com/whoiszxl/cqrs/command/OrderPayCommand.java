package com.whoiszxl.cqrs.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "订单支付命令")
public class OrderPayCommand {

    @Schema(description = "订单ID")
    private String orderId;

    @Schema(description = "支付方式 [1:支付宝 2:微信 3:数字货币]")
    private Integer payType;

    @Schema(description = "如果是数字货币支付还需要选择是哪种数字货币，暂支持：BTC, ETH, ERC20(ZXL)")
    private String dcName;
}
