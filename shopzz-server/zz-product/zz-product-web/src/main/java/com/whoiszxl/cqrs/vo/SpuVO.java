package com.whoiszxl.cqrs.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel(value="SPU详情返回实体")
public class SpuVO {

    @ApiModelProperty("商品id")
    private Long id;

    @ApiModelProperty("商品名称")
    private String name;

    @ApiModelProperty("商品副名称")
    private String subName;

    @ApiModelProperty("默认价格")
    private BigDecimal defaultPrice;

    @ApiModelProperty("商品默认图片地址")
    private String defaultPic;

    @ApiModelProperty("类目ID")
    private Long categoryId;

    @ApiModelProperty("父类目ID")
    private Long parentCategoryId;

    @ApiModelProperty("品牌ID")
    private Long brandId;

    @ApiModelProperty("品牌名称")
    private String brandName;

    @ApiModelProperty("包装清单")
    private String packageList;

    @ApiModelProperty("默认选中的SKU ID")
    private Long defaultSkuId;

}