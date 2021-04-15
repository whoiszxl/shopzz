package com.whoiszxl.zero.entity.dto;

import com.whoiszxl.zero.bean.AbstractObject;
import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * 商品详情页数据表
 * 
 * @author whoiszxl
 * @email whoiszxl@gmail.com
 * @date 2021-04-10 18:07:18
 */
@Data
public class ProductDetailDTO extends AbstractObject implements Serializable {
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
