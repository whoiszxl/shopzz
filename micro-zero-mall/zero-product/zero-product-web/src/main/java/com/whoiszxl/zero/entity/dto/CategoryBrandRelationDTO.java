package com.whoiszxl.zero.entity.dto;

import com.whoiszxl.zero.bean.AbstractObject;
import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * 品牌&分类关联表
 * 
 * @author whoiszxl
 * @email whoiszxl@gmail.com
 * 
 */
@Data
public class CategoryBrandRelationDTO extends AbstractObject implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@Id
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
