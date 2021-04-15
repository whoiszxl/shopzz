package com.whoiszxl.zero.entity.vo;

import com.whoiszxl.zero.bean.AbstractObject;
import lombok.Data;

import java.io.Serializable;

/**
 * 品牌&分类关联表
 * 
 * @author whoiszxl
 * @email whoiszxl@gmail.com
 * @date 2021-04-10 18:07:18
 */
@Data
public class CategoryBrandRelationVO extends AbstractObject implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private Long id;
	/**
	 * 品牌id
	 */
	private Long brandId;
	/**
	 * 分类id
	 */
	private Long categoryId;
	/**
	 * 
	 */
	private String brandName;
	/**
	 * 
	 */
	private String categoryName;

}
