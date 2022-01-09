package com.whoiszxl.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.constants.RedisKeyPrefixConstants;
import com.whoiszxl.dto.SkuDTO;
import com.whoiszxl.entity.Cart;
import com.whoiszxl.entity.query.SaveCartQuery;
import com.whoiszxl.entity.vo.CartDetailVO;
import com.whoiszxl.entity.vo.CartItemVO;
import com.whoiszxl.enums.StatusEnum;
import com.whoiszxl.exception.ExceptionCatcher;
import com.whoiszxl.feign.ProductFeignClient;
import com.whoiszxl.mapper.CartMapper;
import com.whoiszxl.service.CartService;
import com.whoiszxl.utils.JsonUtil;
import com.whoiszxl.utils.RedisUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 购物车记录表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2022-01-09
 */
@Service
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements CartService {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private ProductFeignClient productFeignClient;

    @Override
    public Boolean cartAdd(SaveCartQuery addCartQuery) {
        //0. TODO 对sku进行有效性校验
        ResponseResult<SkuDTO> skuInfoResponse = productFeignClient.getSkuInfoBySkuId(addCartQuery.getSkuId());
        SkuDTO sku = skuInfoResponse.getData();
        if(sku.getIsDeleted() == 1) {
            ExceptionCatcher.catchValidateEx(ResponseResult.buildError("商品无效"));
        }

        //1. 判断redis中是否存在当前的购物车信息
        BoundHashOperations<String, Object, Object> cartHashOps = redisUtils.getHashOps(RedisKeyPrefixConstants.MEMBER_CART_PREFIX + StpUtil.getLoginIdAsString());
        String cartItemJson = (String)cartHashOps.get(addCartQuery.getSkuId().toString());

        if(StringUtils.isBlank(cartItemJson)) {
            CartItemVO cartItemVO = new CartItemVO();

            cartItemVO.setChecked(StatusEnum.OPEN.getCode());
            cartItemVO.setQuantity(addCartQuery.getQuantity());
            cartItemVO.setSkuPic(sku.getImgUrl());
            cartItemVO.setPrice(sku.getPromotionPrice() == null ? sku.getSalePrice() : sku.getPromotionPrice());
            cartItemVO.setSkuId(sku.getId());
            cartItemVO.setSkuName(sku.getSkuName());
            cartItemVO.setProductId(sku.getProductId());

            String jsonValue = JsonUtil.toJson(cartItemVO);
            cartHashOps.put(addCartQuery.getSkuId().toString(), jsonValue);
        }else {
            //在原有购物车商品里累加
            CartItemVO cartItemVO = JsonUtil.fromJson(cartItemJson, CartItemVO.class);
            cartItemVO.setQuantity(cartItemVO.getQuantity() + addCartQuery.getQuantity());
            cartItemVO.setSkuPic(sku.getImgUrl());
            cartItemVO.setPrice(sku.getPromotionPrice() == null ? sku.getSalePrice() : sku.getPromotionPrice());
            cartItemVO.setSkuName(sku.getSkuName());
            cartItemVO.setProductId(sku.getProductId());
            String jsonValue = JsonUtil.toJson(cartItemVO);
            cartHashOps.put(addCartQuery.getSkuId().toString(), jsonValue);
        }

        //TODO 发送MQ，落库
        return true;
    }

    @Override
    public CartDetailVO getCartDetail() {
        String memberId = StpUtil.getLoginIdAsString();

        Map<Object, Object> cartItems = redisUtils.hGetAll(RedisKeyPrefixConstants.MEMBER_CART_PREFIX + memberId);
        if(ObjectUtils.isEmpty(cartItems)) {
            return null;
        }

        List<CartItemVO> cartItemVOList = cartItems.values().stream().map(obj -> {
            String jsonValue = (String) obj;
            return JsonUtil.fromJson(jsonValue, CartItemVO.class);
        }).collect(Collectors.toList());

        CartDetailVO cartDetailVO = new CartDetailVO();
        cartDetailVO.setCartItemVOList(cartItemVOList);

        return cartDetailVO;
    }

    @Override
    public Boolean clearCart() {
        String memberId = StpUtil.getLoginIdAsString();
        redisUtils.delete(RedisKeyPrefixConstants.MEMBER_CART_PREFIX + memberId);
        return true;
    }

    @Override
    public Boolean checkOrUncheckCartItem(Integer isChecked, Long skuId) {
        BoundHashOperations<String, Object, Object> cartHashOps = redisUtils.getHashOps(RedisKeyPrefixConstants.MEMBER_CART_PREFIX + StpUtil.getLoginIdAsString());
        String cartItemJson = (String)cartHashOps.get(skuId.toString());

        if(StringUtils.isBlank(cartItemJson)) {
            return false;
        }

        CartItemVO cartItemVO = JsonUtil.fromJson(cartItemJson, CartItemVO.class);
        cartItemVO.setChecked(isChecked);
        String jsonValue = JsonUtil.toJson(cartItemVO);

        cartHashOps.put(skuId.toString(), jsonValue);
        return true;
    }

    @Override
    public Boolean cartUpdate(Long skuId, Integer quantity) {
        BoundHashOperations<String, Object, Object> cartHashOps = redisUtils.getHashOps(RedisKeyPrefixConstants.MEMBER_CART_PREFIX + StpUtil.getLoginIdAsString());
        String cartItemJson = (String)cartHashOps.get(skuId.toString());

        CartItemVO cartItemVO = JsonUtil.fromJson(cartItemJson, CartItemVO.class);
        if((cartItemVO.getQuantity() + quantity) == 0) {
            //删除
            cartHashOps.delete(skuId.toString());
        }else {
            cartItemVO.setQuantity(cartItemVO.getQuantity() + quantity);
            String jsonValue = JsonUtil.toJson(cartItemVO);
            cartHashOps.put(skuId.toString(), jsonValue);
        }
        return true;
    }

    @Override
    public Boolean deleteCartItem(List<Long> skuIdList) {
        BoundHashOperations<String, Object, Object> cartHashOps = redisUtils.getHashOps(RedisKeyPrefixConstants.MEMBER_CART_PREFIX + StpUtil.getLoginIdAsString());
        cartHashOps.delete(skuIdList.stream().map(Object::toString).toArray());
        return true;
    }
}
