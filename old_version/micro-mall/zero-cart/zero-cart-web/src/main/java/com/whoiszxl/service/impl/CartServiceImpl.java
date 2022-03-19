package com.whoiszxl.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.constants.CartCheckConstants;
import com.whoiszxl.dto.SkuDTO;
import com.whoiszxl.entity.Cart;
import com.whoiszxl.entity.query.CartAddQuery;
import com.whoiszxl.enums.StatusEnum;
import com.whoiszxl.feign.ProductFeignClient;
import com.whoiszxl.mapper.CartMapper;
import com.whoiszxl.service.CartService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * <p>
 * 购物车记录表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-30
 */
@Slf4j
@Service
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements CartService {

    @Autowired
    private ThreadPoolExecutor executor;

    @Autowired
    private ProductFeignClient productFeignClient;

    @Override
    public boolean addProductToCart(CartAddQuery cartAddQuery) {
        Integer quantity = cartAddQuery.getQuantity();
        Long skuId = cartAddQuery.getSkuId();
        //1. 通过会员ID和SKU ID查询购物车表中是否存在这条记录
        long memberId = StpUtil.getLoginIdAsLong();
        LambdaQueryWrapper<Cart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Cart::getMemberId, memberId);
        queryWrapper.eq(Cart::getSkuId, skuId);
        Cart cart = this.getOne(queryWrapper);

        boolean saveOrUpdateFlag = false;
        if(cart == null) {
            //2. 购物车不存在，新增
            Cart addCart = new Cart();
            addCart.setMemberId(memberId);
            addCart.setSkuId(skuId);
            addCart.setQuantity(quantity);
            CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> {
                //3. 查询SKU信息
                ResponseResult<SkuDTO> skuResult = productFeignClient.getSkuInfoBySkuId(skuId);
                SkuDTO sku = skuResult.getData();

                addCart.setProductId(sku.getProductId());
                addCart.setSkuName(sku.getSkuName());
                addCart.setSkuPic(sku.getImgUrl());
                addCart.setPrice(sku.getSalePrice());
                addCart.setChecked(CartCheckConstants.CHECKED);
                addCart.setStatus(StatusEnum.OPEN.getCode());
            }, executor);

            //TODO 查询销售属性
            addCart.setSaleAttr("todo");

            try {
                CompletableFuture.allOf(future1).get();
            } catch (Exception e) {
                log.error("新增购物车失败", e);
            }

            saveOrUpdateFlag = this.save(addCart);
        }else {
            //3. 购物车存在，更新
            cart.setQuantity(cart.getQuantity() + cartAddQuery.getQuantity());
            saveOrUpdateFlag = this.updateById(cart);
        }
        return saveOrUpdateFlag;
    }


    @Override
    public Boolean updateQuantity(long memberId, Long cartId, Integer updateQuantity) {
        //1. 获取购物车，并进行基础校验
        Cart cart = this.getById(cartId);
        if(cart != null && cart.getMemberId().equals(memberId)) {
            //2. 更新数量
            cart.setQuantity(cart.getQuantity() + updateQuantity);
            this.updateById(cart);
            return true;
        }
        return false;
    }
}
