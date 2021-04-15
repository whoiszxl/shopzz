package com.whoiszxl.zero.entity.vo;

import com.whoiszxl.zero.bean.AbstractObject;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 属性分组表
 * 
 * @author whoiszxl
 * @email whoiszxl@gmail.com
 * @date 2021-04-10 18:07:18
 */
@Data
public class AttributeGroupVO extends AbstractObject implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 属性分组主键id
	 */
	private Long id;
	/**
	 * 组名
	 */
	private String groupName;
	/**
	 * 所属分类id
	 */
	private Long categoryId;
	/**
	 * 分组描述
	 */
	private String desc;
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
