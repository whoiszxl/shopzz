package com.whoiszxl.controller;


import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.entity.Cart;
import com.whoiszxl.entity.query.CartAddQuery;
import com.whoiszxl.entity.query.CartUpdateQuantityQuery;
import com.whoiszxl.entity.vo.CartVO;
import com.whoiszxl.exception.ExceptionCatcher;
import com.whoiszxl.service.CartService;
import com.whoiszxl.utils.BeanCopierUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 购物车记录表 前端控制器
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-30
 */
@RestController
@RequestMapping("/cart")
@Api(tags = "购物车相关接口")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    @ApiOperation(value = "添加商品到购物车", notes = "添加商品到购物车", response = ResponseResult.class)
    public ResponseResult<Boolean> cartAdd(@RequestBody CartAddQuery query) {
        boolean addFlag = cartService.addProductToCart(query);
        return ResponseResult.buildByFlag(addFlag);
    }

    @Transactional
    @GetMapping("/list")
    @ApiOperation(value = "获取当前用户的购物车列表", notes = "获取当前用户的购物车列表", response = CartVO.class)
    public ResponseResult<List<CartVO>> cartList() {
        long memberId = StpUtil.getLoginIdAsLong();
        LambdaQueryWrapper<Cart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Cart::getMemberId, memberId);
        List<Cart> cartList = cartService.list(queryWrapper);
        List<CartVO> cartVOList = BeanCopierUtils.copyListProperties(cartList, CartVO::new);
        return ResponseResult.buildSuccess(cartVOList);
    }

    @Transactional
    @PutMapping("/update/quantity")
    @ApiOperation(value = "更新购物车商品数量", notes = "更新购物车商品数量", response = Boolean.class)
    public ResponseResult<Boolean> updateQuantity(@RequestBody CartUpdateQuantityQuery query) {
        long memberId = StpUtil.getLoginIdAsLong();
        Boolean updateFlag = cartService.updateQuantity(memberId, Long.parseLong(query.getCartId()), query.getUpdateQuantity());
        return ResponseResult.buildByFlag(updateFlag);
    }

    @Transactional
    @DeleteMapping("/delete")
    @ApiOperation(value = "批量删除购物车中的商品", notes = "批量删除购物车中的商品", response = ResponseResult.class)
    public ResponseResult<Boolean> cartDelete(@RequestBody List<String> cartIds) {
        //1. 校验传入的ids是否是当前用户的
        long memberId = StpUtil.getLoginIdAsLong();
        Collection<Cart> carts = cartService.listByIds(cartIds);
        carts.forEach(cart -> {
            if(cart.getMemberId() != memberId) {
                ExceptionCatcher.catchValidateEx(ResponseResult.buildError("无权限删除其他用户的购物车"));
            }
        });

        //2. 进行删除操作
        boolean removeFlag = cartService.removeByIds(cartIds);
        return ResponseResult.buildByFlag(removeFlag);
    }

    @Transactional
    @DeleteMapping("/clear")
    @ApiOperation(value = "清空当前用户的购物车", notes = "清空当前用户的购物车", response = ResponseResult.class)
    public ResponseResult<Boolean> cartClear() {
        long memberId = StpUtil.getLoginIdAsLong();
        LambdaQueryWrapper<Cart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Cart::getMemberId, memberId);
        boolean removeFlag = cartService.remove(queryWrapper);
        return ResponseResult.buildByFlag(removeFlag);
    }

}

