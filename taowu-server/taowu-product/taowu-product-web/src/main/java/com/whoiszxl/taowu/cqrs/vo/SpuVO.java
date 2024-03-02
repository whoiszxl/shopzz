package com.whoiszxl.taowu.cqrs.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "SPU详情返回实体")
public class SpuVO {

    @Schema(description = "商品id")
    private Long id;

    @Schema(description = "商品名称")
    private String name;

    @Schema(description = "商品副名称")
    private String subName;

    @Schema(description = "默认价格")
    private BigDecimal defaultPrice;

    @Schema(description = "商品默认图片地址")
    private String defaultPic;

    @Schema(description = "类目ID")
    private Long categoryId;

    @Schema(description = "父类目ID")
    private Long parentCategoryId;

    @Schema(description = "品牌ID")
    private Long brandId;

    @Schema(description = "品牌名称")
    private String brandName;

    @Schema(description = "包装清单")
    private String packageList;

    @Schema(description = "默认选中的SKU ID")
    private Long defaultSkuId;

}