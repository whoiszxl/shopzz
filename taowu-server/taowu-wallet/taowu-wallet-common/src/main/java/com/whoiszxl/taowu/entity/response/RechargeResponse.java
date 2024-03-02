package com.whoiszxl.taowu.entity.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "充值地址返回数据")
public class RechargeResponse implements Serializable {

    @Schema(description = "币种ID")
    private Integer currencyId;

    @Schema(description = "数字货币地址")
    private String address;

    @Schema(description = "二维码数据")
    private String qrCodeData;

}
