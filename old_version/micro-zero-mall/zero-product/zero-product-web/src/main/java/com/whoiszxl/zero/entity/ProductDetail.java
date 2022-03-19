package com.whoiszxl.zero.entity;

import com.whoiszxl.zero.bean.AbstractObject;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 商品详情页数据表
 * 
 * @author whoiszxl
 * @email whoiszxl@gmail.com
 * 
 */
@Data
@Entity
@Table(name="pms_product_detail")
public class ProductDetail extends AbstractObject implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 商品ID
	 */
	@Id
	private Long productId;
	/**
	 * PC商品详情富文本内容
	 */
	private String detailHtml;
	/**
	 * 移动端商品详情富文本内容
	 */
	private String detailMobile;
	/**
	 * 创建时间
	 */
	private Date createdAt;
	/**
	 * 更新时间
	 */
	private Date updatedAt;

}
