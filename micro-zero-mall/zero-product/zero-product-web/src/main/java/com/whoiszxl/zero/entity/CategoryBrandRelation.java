package com.whoiszxl.zero.entity;

import com.whoiszxl.zero.bean.AbstractObject;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 品牌&分类关联表
 * 
 * @author whoiszxl
 * @email whoiszxl@gmail.com
 * @date 2021-04-10 18:07:18
 */
@Data
@Entity
@Table(name="pms_category_brand_relation")
public class CategoryBrandRelation extends AbstractObject implements Serializable {
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
