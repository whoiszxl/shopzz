package com.whoiszxl.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel("订单提交参数")
public class OrderSubmitVO {

    @ApiModelProperty("用户收货地址主键ID")
    private String memberAddressId;

    @ApiModelProperty("优惠券ID")
    private String couponId;

    @ApiModelProperty("用户收货地址主键ID")
    private Integer payType;

    @ApiModelProperty("总支付金额")
    private BigDecimal totalAmount;

    @ApiModelProperty("订单备注")
    private String orderComment;
}
