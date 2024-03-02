package com.whoiszxl.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.core.constants.RedisPrefixConstants;
import com.whoiszxl.core.entity.ResponseResult;
import com.whoiszxl.core.enums.StatusEnum;
import com.whoiszxl.core.exception.ExceptionCatcher;
import com.whoiszxl.core.utils.AuthUtils;
import com.whoiszxl.core.utils.JsonUtil;
import com.whoiszxl.core.utils.RedisUtils;
import com.whoiszxl.cqrs.command.CartAddCommand;
import com.whoiszxl.cqrs.response.CartDetailApiResponse;
import com.whoiszxl.cqrs.response.CartItemVO;
import com.whoiszxl.dto.SkuFeignDTO;
import com.whoiszxl.entity.Cart;
import com.whoiszxl.feign.ProductFeignClient;
import com.whoiszxl.mapper.CartMapper;
import com.whoiszxl.service.CartService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
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
 * @since 2022-04-09
 */
@Service
@RequiredArgsConstructor
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements CartService {

    private final RedisUtils redisUtils;

    private final ProductFeignClient productFeignClient;

    @Override
    public void addCart(CartAddCommand cartAddCommand) {
        //0. 对sku进行有效性校验
        SkuFeignDTO sku = null;
        if(StringUtils.isBlank(cartAddCommand.getSkuCode())) {
            ResponseResult<SkuFeignDTO> skuInfoResponse = productFeignClient.getSkuInfoBySkuId(cartAddCommand.getSkuId());
            sku = skuInfoResponse.getData();
        }else {
            ResponseResult<SkuFeignDTO> skuInfoResponse = productFeignClient.getSkuInfoBySkuCode(cartAddCommand.getSkuCode());
            sku = skuInfoResponse.getData();
        }

        if(sku == null) {
            ExceptionCatcher.catchServiceEx("商品无效");
        }

        Long memberId = AuthUtils.getMemberId();

        //1. 判断redis中是否存在当前的购物车信息
        BoundHashOperations<String, Object, Object> cartHashOps = redisUtils.getHashOps(RedisPrefixConstants.MEMBER_CART_PREFIX + memberId);
        String cartItemJson = (String)cartHashOps.get(sku.getId().toString());

        if(StringUtils.isBlank(cartItemJson)) {
            CartItemVO cartItemVO = new CartItemVO();
            cartItemVO.setMemberId(memberId);
            cartItemVO.setChecked(StatusEnum.OPEN.getCode());
            cartItemVO.setQuantity(cartAddCommand.getQuantity());
            cartItemVO.setSkuPic(sku.getSkuImg());
            cartItemVO.setPrice(sku.getPromotionPrice() == null ? sku.getSalePrice() : sku.getPromotionPrice());
            cartItemVO.setSkuId(sku.getId());
            cartItemVO.setSkuName(sku.getSkuName());
            cartItemVO.setProductId(sku.getSpuId());

            String jsonValue = JsonUtil.toJson(cartItemVO);
            cartHashOps.put(sku.getId().toString(), jsonValue);
        }else {
            //在原有购物车商品里累加
            CartItemVO cartItemVO = JsonUtil.fromJson(cartItemJson, CartItemVO.class);
            cartItemVO.setQuantity(cartItemVO.getQuantity() + cartAddCommand.getQuantity());
            cartItemVO.setSkuPic(sku.getSkuImg());
            cartItemVO.setPrice(sku.getPromotionPrice() == null ? sku.getSalePrice() : sku.getPromotionPrice());
            cartItemVO.setSkuName(sku.getSkuName());
            cartItemVO.setProductId(sku.getSpuId());
            String jsonValue = JsonUtil.toJson(cartItemVO);
            cartHashOps.put(sku.getId().toString(), jsonValue);
        }

        //TODO 发送MQ，落库

    }

    @Override
    public Boolean cartDelete(List<Long> skuIdList) {
        BoundHashOperations<String, Object, Object> cartHashOps = redisUtils.getHashOps(RedisPrefixConstants.MEMBER_CART_PREFIX + StpUtil.getLoginIdAsString());
        cartHashOps.delete(skuIdList.stream().map(Object::toString).toArray());
        return true;
    }

    @Override
    public void cartUpdate(Long skuId, Integer quantity) {
        BoundHashOperations<String, Object, Object> cartHashOps = redisUtils.getHashOps(RedisPrefixConstants.MEMBER_CART_PREFIX + StpUtil.getLoginIdAsString());
        String cartItemJson = (String)cartHashOps.get(skuId.toString());

        CartItemVO cartItemVO = JsonUtil.fromJson(cartItemJson, CartItemVO.class);
        if((cartItemVO.getQuantity() + quantity) == 0) {
            //删除
            cartHashOps.delete(skuId.toString());
        }else {
            cartItemVO.setQuantity(quantity);
            String jsonValue = JsonUtil.toJson(cartItemVO);
            cartHashOps.put(skuId.toString(), jsonValue);
        }
    }

    @Override
    public Boolean checkOrUncheckCartItem(Integer isChecked, Long skuId) {
        BoundHashOperations<String, Object, Object> cartHashOps = redisUtils.getHashOps(RedisPrefixConstants.MEMBER_CART_PREFIX + StpUtil.getLoginIdAsString());
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
    public void cleanCart() {
        String memberId = AuthUtils.getStrMemberId();
        redisUtils.delete(RedisPrefixConstants.MEMBER_CART_PREFIX + memberId);
    }

    @Override
    public CartDetailApiResponse getCartDetail() {
        String memberId = AuthUtils.getStrMemberId();
        CartDetailApiResponse response = new CartDetailApiResponse();

        Map<Object, Object> cartItems = redisUtils.hGetAll(RedisPrefixConstants.MEMBER_CART_PREFIX + memberId);
        if(ObjectUtils.isEmpty(cartItems)) {
            return response;
        }

        List<CartItemVO> cartItemVOList = cartItems.values().stream().map(obj -> {
            String jsonValue = (String) obj;
            return JsonUtil.fromJson(jsonValue, CartItemVO.class);
        }).collect(Collectors.toList());

        response.setCartItemVOList(cartItemVOList);

        return response;
    }

    @Override
    public void clearCheckedCart() {
        BoundHashOperations<String, Object, Object> cartHashOps = redisUtils.getHashOps(RedisPrefixConstants.MEMBER_CART_PREFIX + StpUtil.getLoginIdAsString());
        CartDetailApiResponse cartDetail = getCartDetail();
        for (CartItemVO cartItemVO : cartDetail.getCartItemVOList()) {
            if(cartItemVO.getChecked() == 1) {
                cartHashOps.delete(cartItemVO.getSkuId().toString());
            }
        }
    }
}
