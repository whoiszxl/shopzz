package com.whoiszxl.entity;

import java.util.Date;

public class Category {

	private Integer id;

	private Integer parentId;

	private String name;
	
	private String img;

	private Boolean status;

	private Integer sortOrder;

	private Date createTime;

	private Date updateTime;

	public Category(Integer id, Integer parentId, String name, String img, Boolean status, Integer sortOrder,
			Date createTime, Date updateTime) {
		super();
		this.id = id;
		this.parentId = parentId;
		this.name = name;
		this.img = img;
		this.status = status;
		this.sortOrder = sortOrder;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}

	
	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public Category() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Integer getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}