package com.whoiszxl.vo;

import java.util.List;

public class SkuVo {

	private Integer id;

    private String skuTitle;

    private List<String> skuContent;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSkuTitle() {
		return skuTitle;
	}

	public void setSkuTitle(String skuTitle) {
		this.skuTitle = skuTitle;
	}

	public List<String> getSkuContent() {
		return skuContent;
	}

	public void setSkuContent(List<String> skuContent) {
		this.skuContent = skuContent;
	}
    

    
}
