package com.whoiszxl.zero.entity.vo;

import com.whoiszxl.zero.bean.AbstractObject;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 属性&属性分组关联表
 * 
 * @author whoiszxl
 * @email whoiszxl@gmail.com
 * @date 2021-04-10 18:07:18
 */
@Data
public class AttributeAttrgroupRelationVO extends AbstractObject implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 属性id
	 */
	private Long attributeId;
	/**
	 * 属性分组id
	 */
	private Long attributeGroupId;
	/**
	 * 创建时间
	 */
	private Date createdAt;
	/**
	 * 更新时间
	 */
	private Date updatedAt;

}
