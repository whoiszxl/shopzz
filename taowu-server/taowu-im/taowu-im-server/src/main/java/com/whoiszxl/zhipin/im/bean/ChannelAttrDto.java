package com.whoiszxl.zhipin.im.bean;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * channel attr dto
 * @author whoiszxl
 */
@Data
@Builder
public class ChannelAttrDto {

    @Schema(description = "用户ID")
    private String memberId;

    @Schema(description = "客户端类型")
    private byte clientType;

    @Schema(description = "设备IMEI号")
    private String imei;
}
