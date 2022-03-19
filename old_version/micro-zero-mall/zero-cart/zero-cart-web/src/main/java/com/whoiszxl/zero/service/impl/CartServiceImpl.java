package com.whoiszxl.zero.service.impl;

import com.whoiszxl.zero.bean.BaseParam;
import com.whoiszxl.zero.bean.Result;
import com.whoiszxl.zero.dao.CartDao;
import com.whoiszxl.zero.dto.CartDTO;
import com.whoiszxl.zero.dto.SkuDTO;
import com.whoiszxl.zero.entity.Cart;
import com.whoiszxl.zero.entity.params.CartAddParam;
import com.whoiszxl.zero.entity.params.CartDeleteParam;
import com.whoiszxl.zero.entity.params.CartQuantityUpdateParam;
import com.whoiszxl.zero.enums.StatusEnum;
import com.whoiszxl.zero.feign.ProductFeign;
import com.whoiszxl.zero.service.CartService;
import com.whoiszxl.zero.utils.BeanCopierUtils;
import com.whoiszxl.zero.utils.IdWorker;
import com.whoiszxl.zero.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartDao cartDao;

    @Autowired
    private ProductFeign productFeign;

    @Autowired
    private IdWorker idWorker;

    @Override
    @Transactional
    public int deleteAll() {
        Long memberId = JwtUtils.getMemberId();
        return cartDao.deleteCartsByMemberId(memberId);
    }

    @Override
    @Transactional
    public int delete(CartDeleteParam cartDeleteParam) {
        Long memberId = JwtUtils.getMemberId();
        return cartDao.deleteCartByIdAndMemberId(cartDeleteParam.getId(), memberId);
    }

    @Override
    @Transactional
    public int quantityUpdate(CartQuantityUpdateParam cartQuantityUpdateParam) {
        Long memberId = JwtUtils.getMemberId();
        Cart cart = cartDao.findByIdAndMemberIdAndStatus(cartQuantityUpdateParam.getId(), memberId, StatusEnum.OPEN.getCode());
        cart.setQuantity(cart.getQuantity() + cartQuantityUpdateParam.getNum());
        cartDao.saveAndFlush(cart);
        return 1;
    }

    @Override
    public int add(CartAddParam cartAddParam) {
        //查询购物车是否存在
        Long memberId = JwtUtils.getMemberId();
        Cart cart = cartDao.findBySkuIdAndMemberIdAndStatus(cartAddParam.getSkuId(), memberId, StatusEnum.OPEN.getCode());
        if(cart == null) {
            //新增记录
            SkuDTO skuDTO = productFeign.skuInfo(BaseParam.builder().id(cartAddParam.getSkuId()).build());
            Cart insertCart = new Cart();
            insertCart.setId(idWorker.nextId());
            insertCart.setMemberId(memberId);
            insertCart.setProductId(skuDTO.getProductId());
            insertCart.setSkuId(skuDTO.getId());
            insertCart.setSkuName(skuDTO.getSkuName());
            insertCart.setSkuPic(skuDTO.getImgUrl());
            insertCart.setQuantity(cartAddParam.getNum());
            insertCart.setPrice(skuDTO.getSalePrice());
            insertCart.setSaleAttr("");
            insertCart.setChecked(StatusEnum.OPEN.getCode());
            insertCart.setStatus(StatusEnum.OPEN.getCode());

            cartDao.saveAndFlush(insertCart);
        }else {
            //新增购物车数量
            cart.setQuantity(cart.getQuantity() + cartAddParam.getNum());
            cartDao.saveAndFlush(cart);
        }
        return 1;
    }

    @Override
    public List<CartDTO> list() {
        List<Cart> carts = cartDao.findAll();
        return BeanCopierUtils.copyListProperties(carts, CartDTO::new);
    }

    @Override
    public List<CartDTO> findAllCheckedCartByMemberId(Long memberId) {
        List<Cart> carts = cartDao.findAllByMemberIdAndChecked(memberId, StatusEnum.OPEN.getCode());
        List<CartDTO> cartDTOS = BeanCopierUtils.copyListProperties(carts, CartDTO::new);
        return cartDTOS;
    }
}
