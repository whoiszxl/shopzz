package com.whoiszxl.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.whoiszxl.common.ServerResponse;
import com.whoiszxl.dao.ShippingMapper;
import com.whoiszxl.entity.Shipping;
import com.whoiszxl.service.ShippingService;

/**
 * 
 * @author whoiszxl
 *
 */
@Service
public class ShippingServiceImpl implements ShippingService {

	@Autowired
	private ShippingMapper shippingMapper;
	
	@Override
	public ServerResponse add(Integer userId, Shipping shipping) {
		shipping.setUserId(userId);
		int rowCount = shippingMapper.insert(shipping);
		if(rowCount > 0) {
//			HashMap<Object, Object> result = Maps.newHashMap();
//			result.put("shippingId", shipping.getId());
			return ServerResponse.createBySuccess("新建地址成功");
		}
		
		return ServerResponse.createByErrorMessage("新建地址失败");
	}

	@Override
	public ServerResponse<String> del(Integer userId, Integer shippingId) {
		int resultCount = shippingMapper.deleteByShippingIdUserId(userId, shippingId);
		if(resultCount > 0) {
		    return ServerResponse.createBySuccess("删除地址成功");
        }
        return ServerResponse.createByErrorMessage("删除地址失败");
	}

	@Override
	public ServerResponse update(Integer userId, Shipping shipping) {
		shipping.setUserId(userId);
        int rowCount = shippingMapper.updateByShipping(shipping);
        if(rowCount > 0){
            return ServerResponse.createBySuccess("更新地址成功");
        }
        return ServerResponse.createByErrorMessage("更新地址失败");
	}

	@Override
	public ServerResponse<Shipping> select(Integer userId, Integer shippingId) {
		Shipping shipping = shippingMapper.selectByShippingIdUserId(userId,shippingId);
        if(shipping == null){
            return ServerResponse.createByErrorMessage("无法查询到该地址");
        }
        return ServerResponse.createBySuccess("更新地址成功",shipping);
	}

	@Override
	public ServerResponse<PageInfo> list(Integer userId, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum,pageSize);
        List<Shipping> shippingList = shippingMapper.selectByUserId(userId);
        PageInfo pageInfo = new PageInfo(shippingList);
        return ServerResponse.createBySuccess(pageInfo);
	}

}

