package com.whoiszxl.service;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.whoiszxl.common.ServerResponse;
import com.whoiszxl.vo.OrderVo;

/**
 * 
 * @author whoiszxl
 *
 */
public interface OrderService {

	ServerResponse pay(Long orderNo,Integer userId,String path);
	ServerResponse aliCallback(Map<String, String> params);
	ServerResponse queryOrderPayStatus(Integer userId,Long orderNo);
	
	ServerResponse createOrder(Integer userId,Integer shippingId);
    ServerResponse<String> cancel(Integer userId,Long orderNo);
    ServerResponse getOrderCartProduct(Integer userId);
    ServerResponse<OrderVo> getOrderDetail(Integer userId, Long orderNo);
    ServerResponse<PageInfo> getOrderList(Integer userId, int pageNum, int pageSize);
    
    
    //backend
    ServerResponse<PageInfo> manageList(int pageNum,int pageSize);
    ServerResponse<OrderVo> manageDetail(Long orderNo);
    ServerResponse<PageInfo> manageSearch(Long orderNo,int pageNum,int pageSize);
    ServerResponse<String> manageSendGoods(Long orderNo);
}
