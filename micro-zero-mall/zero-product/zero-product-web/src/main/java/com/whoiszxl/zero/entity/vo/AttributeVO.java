package com.whoiszxl.zero.entity.vo;

import com.whoiszxl.zero.bean.AbstractObject;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 商品属性
 * 
 * @author whoiszxl
 * @email whoiszxl@gmail.com
 * @date 2021-04-10 18:07:18
 */
@Data
public class AttributeVO extends AbstractObject implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 属性主键id
	 */
	private Long id;
	/**
	 * 属性名称
	 */
	private String attributeName;
	/**
	 * 属性图标地址
	 */
	private String icon;
	/**
	 * 是否需要检索[0-不需要，1-需要]
	 */
	private Integer searchType;
	/**
	 * 值类型[0-为单个值，1-可以选择多个值]
	 */
	private Integer valueType;
	/**
	 * 可选值列表[用逗号分隔]
	 */
	private String valueSelect;
	/**
	 * 属性类型[0-销售属性，1-基本属性]
	 */
	private Integer attributeType;
	/**
	 * 启用状态[0 - 禁用，1 - 启用]
	 */
	private Integer status;
	/**
	 * 创建时间
	 */
	private Date createdAt;
	/**
	 * 更新时间
	 */
	private Date updatedAt;

}
