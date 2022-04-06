package com.whoiszxl.cqrs.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel(value = "SKU返回实体")
public class SkuVO {

    @ApiModelProperty("sku主键ID")
    private Long id;

    @ApiModelProperty("商品SPU的ID")
    private Long spuId;

    @ApiModelProperty("所属分类id")
    private Long categoryId;

    @ApiModelProperty("父类目ID")
    private Long parentCategoryId;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("sku缩略图片地址")
    private String skuImg;

    @ApiModelProperty("销售价格")
    private BigDecimal salePrice;

    @ApiModelProperty("促销价格")
    private BigDecimal promotionPrice;

    @ApiModelProperty("商品销售属性,json格式")
    private String saleAttr;

    @ApiModelProperty("SKU编码")
    private String skuCode;
}
