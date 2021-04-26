package com.whoiszxl.zero.entity.vo;

import com.whoiszxl.zero.bean.AbstractObject;
import lombok.Data;

import java.io.Serializable;

/**
 * sku销售属性值表
 * 
 * @author whoiszxl
 * @email whoiszxl@gmail.com
 * 
 */
@Data
public class SkuSaleAttributeValueVO extends AbstractObject implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	private Long id;
	/**
	 * sku_id
	 */
	private Long skuId;
	/**
	 * attribute_id
	 */
	private Long attributeId;
	/**
	 * 销售属性名
	 */
	private String attributeName;
	/**
	 * 销售属性值
	 */
	private String attributeValue;
	/**
	 * 顺序
	 */
	private Integer attributeSort;

}
