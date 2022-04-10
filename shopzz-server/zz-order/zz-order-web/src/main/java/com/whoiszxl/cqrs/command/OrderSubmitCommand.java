package com.whoiszxl.cqrs.command;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 订单提交命令
 *
 * @author whoiszxl
 * @date 2022/1/12
 */
@Data
@ApiModel("订单提交命令")
public class OrderSubmitCommand {

    @ApiModelProperty("地址ID")
    private Long addressId;

    @ApiModelProperty("订单token")
    private String orderToken;

    @ApiModelProperty("使用积分")
    private String usePoints;

    @ApiModelProperty("订单备注")
    private String orderComment;

    @ApiModelProperty("优惠券ID")
    private Long couponId;
}
