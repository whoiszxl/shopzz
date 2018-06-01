package com.whoiszxl.oss.config;

import com.whoiszxl.utils.PropertiesUtil;

/**
 * 云配置基類
 * @author whoiszxl
 *
 */
public class BaseConfig {
	
	/**
	 * http url前綴地址
	 */
	private String httpBase = PropertiesUtil.getProperty("qiniu.httpBase");

	public String getHttpBase() {
		return httpBase;
	}

	public void setHttpBase(String httpBase) {
		this.httpBase = httpBase;
	}
	
	
	public static void main(String[] args) {
		System.out.println(PropertiesUtil.getProperty("qiniu.httpBase"));
	}
	
}
