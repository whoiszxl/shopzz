package com.whoiszxl.zero.entity.vo;

import com.whoiszxl.zero.bean.AbstractObject;
import lombok.Data;

import java.io.Serializable;

/**
 * 商品SPU图片表
 * 
 * @author whoiszxl
 * @email whoiszxl@gmail.com
 */
@Data
public class ProductImagesVO extends AbstractObject implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
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
