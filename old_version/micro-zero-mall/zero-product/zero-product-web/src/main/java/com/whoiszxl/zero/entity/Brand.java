package com.whoiszxl.zero.entity;

import com.whoiszxl.zero.bean.AbstractObject;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 品牌表
 * 
 * @author whoiszxl
 * @email whoiszxl@gmail.com
 * 
 */
@Data
@Entity
@Table(name="pms_brand")
public class Brand extends AbstractObject implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 品牌主键id
	 */
	@Id
	private Long id;
	/**
	 * 品牌中文名
	 */
	private String chineseName;
	/**
	 * 品牌英文名
	 */
	private String englishName;
	/**
	 * 品牌别名
	 */
	private String aliasName;
	/**
	 * 首字母
	 */
	private String firstLetter;
	/**
	 * 品牌logo地址
	 */
	private String logo;
	/**
	 * 品牌介绍
	 */
	private String description;
	/**
	 * 品牌授权图片
	 */
	private String authPic;
	/**
	 * 品牌所在地
	 */
	private String location;
	/**
	 * 显示状态[0-不显示；1-显示]
	 */
	private Integer showStatus;
	/**
	 * 品牌说明备注
	 */
	private String remark;
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
