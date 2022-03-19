package com.whoiszxl.zero.dto;

import com.whoiszxl.zero.bean.AbstractObject;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * sku信息表
 * 
 * @author whoiszxl
 * @email whoiszxl@gmail.com
 */
@Data
public class SkuDTO extends AbstractObject implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * sku主键ID
	 */
	private Long id;
	/**
	 * 商品SPU的ID
	 */
	private Long productId;
	/**
	 * 所属分类id
	 */
	private Long categoryId;
	/**
	 * sku名稱
	 */
	private String skuName;
	/**
	 * 品牌id
	 */
	private Long brandId;
	/**
	 * sku缩略图片地址
	 */
	private String imgUrl;
	/**
	 * 采购价格
	 */
	private BigDecimal purchasePrice;
	/**
	 * 促销价格
	 */
	private BigDecimal promotionPrice;
	/**
	 * 销售价格
	 */
	private BigDecimal salePrice;
	/**
	 * 商品销售属性，json格式
	 */
	private String saleData;
	/**
	 * 创建时间
	 */
	private Date createdAt;
	/**
	 * 更新时间
	 */
	private Date updatedAt;

}
