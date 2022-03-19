package com.whoiszxl.zero.entity.dto;

import com.whoiszxl.zero.bean.AbstractObject;
import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 商品三级分类表
 * 
 * @author whoiszxl
 * @email whoiszxl@gmail.com
 * 
 */
@Data
public class CategoryDTO extends AbstractObject implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 分类id
	 */
	@Id
	private Long id;
	/**
	 * 父类目的主键
	 */
	private Long parentId;
	/**
	 * 分类名称
	 */
	private String name;
	/**
	 * 分类级别：1->1级；2->2级 3->3级
	 */
	private Integer level;
	/**
	 * 是否显示[0-不显示，1显示]
	 */
	private Integer status;
	/**
	 * 图标地址
	 */
	private String icon;
	/**
	 * 排序
	 */
	private Integer sort;

	/**
	 * 子目录
	 */
	private List<CategoryDTO> children;


}
