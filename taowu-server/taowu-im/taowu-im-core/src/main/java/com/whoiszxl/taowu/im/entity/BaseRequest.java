package com.whoiszxl.taowu.im.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 基础请求类
 * @author whoiszxl
 */
@Data
@Schema(description = "基础请求类")
public class BaseRequest {

    @Schema(description = "客户端类型")
    private Integer clientType;

    @Schema(description = "移动设备唯一识别码")
    private String imei;



}
