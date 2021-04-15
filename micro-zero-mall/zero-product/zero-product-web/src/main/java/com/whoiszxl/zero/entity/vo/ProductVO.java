package com.whoiszxl.zero.entity.vo;

import com.whoiszxl.zero.bean.AbstractObject;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品基础信息表
 * 
 * @author whoiszxl
 * @email whoiszxl@gmail.com
 * @date 2021-04-10 18:07:19
 */
@Data
public class ProductVO extends AbstractObject implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 商品id
	 */
	private Long id;
	/**
	 * 商品名称
	 */
	private String name;
	/**
	 * 商品副名称
	 */
	private String subName;
	/**
	 * 商品默认图片地址
	 */
	private String defaultPic;
	/**
	 * 商品默认价格
	 */
	private String defaultPrice;
	/**
	 * 类目ID
	 */
	private Long categoryId;
	/**
	 * 品牌ID
	 */
	private Long brandId;
	/**
	 * 品牌名称
	 */
	private String brandName;
	/**
	 * 商品毛重，单位:g
	 */
	private BigDecimal grossWeight;
	/**
	 * 外包装长度，单位:cm
	 */
	private BigDecimal length;
	/**
	 * 外包装宽，单位:cm
	 */
	private BigDecimal width;
	/**
	 * 外包装高，单位:cm
	 */
	private BigDecimal height;
	/**
	 * 服务保证，多标语逗号隔开
	 */
	private String serviceGuarantees;
	/**
	 * 包装清单
	 */
	private String packageList;
	/**
	 * 运费模板ID
	 */
	private Long freightTemplateId;

}
