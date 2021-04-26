package com.whoiszxl.zero.entity.dto;

import com.whoiszxl.zero.bean.AbstractObject;
import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * 属性&属性分组关联表
 * 
 * @author whoiszxl
 * @email whoiszxl@gmail.com
 * 
 */
@Data
public class AttributeAttrgroupRelationDTO extends AbstractObject implements Serializable {
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
