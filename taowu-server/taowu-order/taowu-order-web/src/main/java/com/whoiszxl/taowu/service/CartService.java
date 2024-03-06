package com.whoiszxl.taowu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whoiszxl.taowu.cqrs.command.CartAddCommand;
import com.whoiszxl.taowu.cqrs.response.CartDetailApiResponse;
import com.whoiszxl.taowu.entity.Cart;

import java.util.List;

/**
 * <p>
 * 购物车记录表 服务类
 * </p>
 *
 * @author whoiszxl
 * @since 2022-04-09
 */
public interface CartService extends IService<Cart> {

    /**
     * 添加购物车
     * @param cartAddCommand 购物车添加命令: skuId + quantity数量
     */
    void addCart(CartAddCommand cartAddCommand);

    /**
     * 删除购物车
     * @param skuIdList sku id集合
     * @return
     */
    Boolean cartDelete(List<Long> skuIdList);

    /**
     * 更新购物车
     * @param skuId sku id
     * @param quantity 更新数量
     */
    void cartUpdate(Long skuId, Integer quantity);

    /**
     * 选中或不选中购物车item
     * @param isChecked 选中 or 不选中
     * @param skuId sku id
     * @return
     */
    Boolean checkOrUncheckCartItem(Integer isChecked, Long skuId);

    /**
     * 清空购物车
     */
    void cleanCart();

    /**
     * 获取当前购物车信息
     * @return 购物车信息
     */
    CartDetailApiResponse getCartDetail();

    /**
     * 清空购物车选中的item
     */
    void clearCheckedCart();


}
