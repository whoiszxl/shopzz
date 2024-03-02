package com.whoiszxl.taowu.cqrs.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "SKU返回实体")
public class SkuVO {

    @Schema(description = "sku主键ID")
    private Long id;

    @Schema(description = "商品SPU的ID")
    private Long spuId;

    @Schema(description = "所属分类id")
    private Long categoryId;

    @Schema(description = "父类目ID")
    private Long parentCategoryId;

    @Schema(description = "sku名称")
    private String skuName;

    @Schema(description = "sku缩略图片地址")
    private String skuImg;

    @Schema(description = "销售价格")
    private BigDecimal salePrice;

    @Schema(description = "促销价格")
    private BigDecimal promotionPrice;

    @Schema(description = "商品销售属性,json格式")
    private String saleAttr;

    @Schema(description = "SKU编码")
    private String skuCode;
}
