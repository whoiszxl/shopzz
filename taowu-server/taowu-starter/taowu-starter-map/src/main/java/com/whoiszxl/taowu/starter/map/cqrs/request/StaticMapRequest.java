package com.whoiszxl.taowu.starter.map.cqrs.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author whoiszxl
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "静态地图获取请求参数")
public class StaticMapRequest implements Serializable {

    @Schema(description = "经纬度，如：112.96846,28.19180")
    private String location;

    @Schema(description = "地图缩放级别:[1,17]")
    private Integer zoom;

    @Schema(description = "图片宽度*图片高度。最大值为1024*1024")
    private String size;

    @Schema(description = "标注位置，如: mid,0xFFaaaa,C:112.96846,28.19180")
    private String markers;

    @Schema(description = "秘钥")
    private String key;


}
