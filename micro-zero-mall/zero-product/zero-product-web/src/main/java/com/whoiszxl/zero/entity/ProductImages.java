package com.whoiszxl.zero.entity;

import com.whoiszxl.zero.bean.AbstractObject;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 商品SPU图片表
 * 
 * @author whoiszxl
 * @email whoiszxl@gmail.com
 * @date 2021-04-10 18:07:19
 */
@Data
@Entity
@Table(name="pms_product_images")
public class ProductImages extends AbstractObject implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Id
	private Long id;
	/**
	 * 商品ID
	 */
	private Long productId;
	/**
	 * 图片名
	 */
	private String imgName;
	/**
	 * 图片地址
	 */
	private String imgUrl;
	/**
	 * 排序，降序排列
	 */
	private Integer sort;
	/**
	 * 是否默认图
	 */
	private Integer defaultImg;

}
