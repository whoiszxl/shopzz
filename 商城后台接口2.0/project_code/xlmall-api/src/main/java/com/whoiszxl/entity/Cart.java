package com.whoiszxl.entity;

import java.util.Date;

public class Cart {

	private Integer id;

	private Integer userId;

	private Integer productId;

	private Integer quantity;

	private Integer checked;

	private Date createTime;

	private Date updateTime;

	public Cart(Integer id, Integer userId, Integer productId, Integer quantity, Integer checked, Date createTime,
			Date updateTime) {
		this.id = id;
		this.userId = userId;
		this.productId = productId;
		this.quantity = quantity;
		this.checked = checked;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}

	public Cart() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getChecked() {
		return checked;
	}

	public void setChecked(Integer checked) {
		this.checked = checked;
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