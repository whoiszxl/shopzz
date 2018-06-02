package com.whoiszxl.entity;

import java.io.Serializable;
import java.util.Date;

public class Banner implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

	private String title;

	private String url;

	private Integer sort;

	private Date createTime;

	private Date updateTime;

}