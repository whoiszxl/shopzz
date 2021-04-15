package com.whoiszxl.zero.entity.dto;

import com.whoiszxl.zero.bean.AbstractObject;
import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * sku销售属性值表
 * 
 * @author whoiszxl
 * @email whoiszxl@gmail.com
 * @date 2021-04-10 18:07:18
 */
@Data
public class SkuSaleAttributeValueDTO extends AbstractObject implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Id
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
