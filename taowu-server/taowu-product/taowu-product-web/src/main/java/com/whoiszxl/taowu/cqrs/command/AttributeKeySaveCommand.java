package com.whoiszxl.taowu.cqrs.command;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 属性key保存命令
 *
 * @author whoiszxl
 * @date 2022/3/21
 */
@Data
@Schema(description = "属性key保存命令")
@JsonIgnoreProperties(ignoreUnknown = true)
public class AttributeKeySaveCommand {

    @Schema(description = "属性名称")
    private String name;

    @Schema(description = "属性单位")
    private String unit;

    @Schema(description = "是否为标准属性")
    private Integer standard;

    @Schema(description = "属性类型[0-销售属性,1-基本属性]")
    private Integer type;

}
