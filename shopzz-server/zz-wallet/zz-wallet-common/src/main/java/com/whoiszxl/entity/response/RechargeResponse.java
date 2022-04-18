package com.whoiszxl.entity.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel("充值地址返回数据")
@Data
public class RechargeResponse implements Serializable {

    @ApiModelProperty("币种ID")
    private Integer currencyId;

    @ApiModelProperty("数字货币地址")
    private String address;

    @ApiModelProperty("二维码数据")
    private String qrCodeData;

    public RechargeResponse(Integer currencyId, String address, String qrCodeData) {
        this.currencyId = currencyId;
        this.address = address;
        this.qrCodeData = qrCodeData;
    }
}
