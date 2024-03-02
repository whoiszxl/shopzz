package com.whoiszxl.taowu.cqrs.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * spu下的属性item
 *
 * @author whoiszxl
 * @date 2022/4/7
 */
@Data
@Schema(description = "spu下的属性item")
public class SpuAttrDTO {

    @Schema(description = "属性key id")
    private Long keyId;

    @Schema(description = "属性key名称")
    private String key;

    @Schema(description = "属性value id")
    private Long valueId;

    @Schema(description = "属性value名称")
    private String value;

}
