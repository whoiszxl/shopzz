package com.whoiszxl.cqrs.dto;

import com.whoiszxl.entity.Signature;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "签名表")
public class SignatureDTO extends Signature {

    @ApiModelProperty("是否选中")
    private boolean selected;

    @ApiModelProperty(value = "三方通道签名")
    private String channelSignatureCode;

    @ApiModelProperty(value = "通道id")
    private String channelId;
}
