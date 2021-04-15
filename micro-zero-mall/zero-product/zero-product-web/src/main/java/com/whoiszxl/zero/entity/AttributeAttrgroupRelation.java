package com.whoiszxl.zero.entity;

import com.whoiszxl.zero.bean.AbstractObject;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
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
@Entity
@Table(name = "pms_attribute_attrgroup_relation")
public class AttributeAttrgroupRelation extends AbstractObject implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 属性id
	 */
	@Id
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
