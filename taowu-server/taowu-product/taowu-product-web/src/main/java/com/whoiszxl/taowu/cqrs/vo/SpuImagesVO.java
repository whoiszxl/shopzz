package com.whoiszxl.taowu.cqrs.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "spu图片vo")
public class SpuImagesVO {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "商品ID")
    private Long spuId;

    @Schema(description = "图片地址")
    private String imgUrl;

    @Schema(description = "排序,降序排列")
    private Integer sort;

    @Schema(description = "是否默认图")
    private Integer isDefault;
}