package com.whoiszxl.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 前端响应对象
 * @author whoiszxl
 *
 */
public class JsonData {

	private boolean ret;
	private String msg;
	private Object data;
	
	public JsonData(boolean ret) {
		this.ret = ret;
	}
	
	public static JsonData success(Object object, String msg) {
		JsonData jsonData = new JsonData(true);
		jsonData.data = object;
		jsonData.msg = msg;
		return jsonData;
	}
	
	public static JsonData success(String msg) {
		JsonData jsonData = new JsonData(true);
		jsonData.msg = msg;
		return jsonData;
	}
	
	public static JsonData success(Object object) {
		JsonData jsonData = new JsonData(true);
		jsonData.data = object;
		return jsonData;
	}
	
	public static JsonData success() {
		return new JsonData(true);
	}
	
	public static JsonData fail(String msg) {
		JsonData jsonData = new JsonData(false);
		jsonData.msg = msg;
		return jsonData;
	}
	
	public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("ret", ret);
        result.put("msg", msg);
        result.put("data", data);
        return result;
    }
	
	public boolean isRet() {
		return ret;
	}
	public void setRet(boolean ret) {
		this.ret = ret;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
	
}
