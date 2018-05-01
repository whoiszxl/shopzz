package com.whoiszxl.service;

import com.whoiszxl.common.ServerResponse;
import com.whoiszxl.vo.CartVo;

public interface CartService {
	
	/**
	 * 添加商品到购物车
	 * @param userId 用户id
	 * @param productId 商品id
	 * @param count 商品数量
	 * @return 
	 */
	ServerResponse<CartVo> add(Integer userId, Integer productId, Integer count);
	ServerResponse<CartVo> update(Integer userId, Integer productId, Integer count);
	ServerResponse<CartVo> deleteProduct(Integer userId,String productIds);

    ServerResponse<CartVo> list (Integer userId);
    ServerResponse<CartVo> selectOrUnSelect (Integer userId,Integer productId,Integer checked);
    ServerResponse<Integer> getCartProductCount(Integer userId);
}
