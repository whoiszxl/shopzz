package com.whoiszxl.zero.entity;

import com.whoiszxl.zero.bean.AbstractObject;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品基础信息表
 * 
 * @author whoiszxl
 * @email whoiszxl@gmail.com
 */
@Data
@Entity
@Table(name="pms_product")
public class Product extends AbstractObject implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 商品id
	 */
	@Id
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
	 * 默认价格
	 */
	private String defaultPrice;
	/**
	 * 商品默认图片地址
	 */
	private String defaultPic;
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
	 * 删除状态：0->未删除；1->已删除
	 */
	private Integer deleteStatus;
	/**
	 * 上架状态：0->下架；1->上架
	 */
	private Integer publishStatus;
	/**
	 * 审核状态：0->未审核；1->审核通过
	 */
	private Integer verifyStatus;
	/**
	 * 包装清单
	 */
	private String packageList;
	/**
	 * 运费模板ID
	 */
	private Long freightTemplateId;
	/**
	 * 创建时间
	 */
	private Date createdAt;
	/**
	 * 更新时间
	 */
	private Date updatedAt;

}
