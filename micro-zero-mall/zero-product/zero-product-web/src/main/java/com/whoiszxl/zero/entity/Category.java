package com.whoiszxl.zero.entity;

import com.whoiszxl.zero.bean.AbstractObject;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 商品三级分类表
 * 
 * @author whoiszxl
 * @email whoiszxl@gmail.com
 * @date 2021-04-10 18:07:18
 */
@Data
@Entity
@Table(name="pms_category")
public class Category extends AbstractObject implements Serializable {
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
	 * 创建时间
	 */
	private Date createdAt;
	/**
	 * 更新时间
	 */
	private Date updatedAt;

}
