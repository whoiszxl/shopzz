package com.whoiszxl.service;

import com.github.pagehelper.PageInfo;
import com.whoiszxl.common.ServerResponse;
import com.whoiszxl.entity.Shipping;

/**
 * 
 * @author whoiszxl
 *
 */
public interface ShippingService {

	ServerResponse add(Integer userId, Shipping shipping);
    ServerResponse<String> del(Integer userId,Integer shippingId);
    ServerResponse update(Integer userId, Shipping shipping);
    ServerResponse<Shipping> select(Integer userId, Integer shippingId);
    ServerResponse<PageInfo> list(Integer userId, int pageNum, int pageSize);
	ServerResponse setDefault(Integer id, int shippingId);
}
