package com.whoiszxl.service;

import com.whoiszxl.entity.Cart;
import com.baomidou.mybatisplus.extension.service.IService;
import com.whoiszxl.entity.query.SaveCartQuery;
import com.whoiszxl.entity.vo.CartDetailVO;

import java.util.List;

/**
 * <p>
 * 购物车记录表 服务类
 * </p>
 *
 * @author whoiszxl
 * @since 2022-01-09
 */
public interface CartService extends IService<Cart> {

    /**
     * 将商品添加到购物车中
     * @param addCartQuery 加购参数
     * @return
     */
    Boolean cartAdd(SaveCartQuery addCartQuery);

    /**
     * 获取当前登录用户的购物车详情
     * @return
     */
    CartDetailVO getCartDetail();

    /**
     * 清空当前登录用户的购物车
     * @return
     */
    Boolean clearCart();

    /**
     * 选中或取消选中购物车里的商品
     * @param isChecked
     * @param skuId
     * @return
     */
    Boolean checkOrUncheckCartItem(Integer isChecked, Long skuId);

    /**
     * 更新购物车SKU数量
     * @param skuId
     * @param quantity
     * @return
     */
    Boolean cartUpdate(Long skuId, Integer quantity);

    /**
     * 删除购物车里的商品
     * @param skuIdList
     * @return
     */
    Boolean deleteCartItem(List<Long> skuIdList);
}
