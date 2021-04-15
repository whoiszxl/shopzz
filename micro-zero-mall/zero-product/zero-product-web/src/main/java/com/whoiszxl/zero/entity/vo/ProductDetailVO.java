package com.whoiszxl.zero.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 商品详情组装VO
 */
@ApiModel("商品详情组装VO")
@Data
public class ProductDetailVO {

	@ApiModelProperty("商品SPU基本信息")
	private ProductVO productVO;

	@ApiModelProperty("商品SKU基本信息")
	private List<SkuVO> skus;

	@ApiModelProperty("商品SPU图片")
	private List<ProductImagesVO> images;

	@ApiModelProperty("商品销售属性的组合")
	private List<SkuDetailSaleAttributeVO> saleAttr;

	@ApiModelProperty("商品销售属性的组合分组参数")
	private List<SaleAttrGroupVO> saleAttrGroup;

	@ApiModelProperty("SPU的规格属性")
	private List<SpuDetailAttrGroupVo> groupAttrs;
}
