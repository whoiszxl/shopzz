package com.whoiszxl.zero.entity;

import com.whoiszxl.zero.bean.AbstractObject;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 商品属性
 * 
 * @author whoiszxl
 * @email whoiszxl@gmail.com
 * 
 */
@Data
@Entity
@Table(name="pms_attribute")
public class Attribute extends AbstractObject implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 属性主键id
	 */
	@Id
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
