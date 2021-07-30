package com.whoiszxl.controller;


import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.entity.Cart;
import com.whoiszxl.entity.query.CartUpdateQuantityQuery;
import com.whoiszxl.entity.vo.CartVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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
public class CartController {

    @PostMapping("/add")
    @ApiOperation(value = "添加商品到购物车", notes = "添加商品到购物车", response = ResponseResult.class)
    public ResponseResult<Boolean> cartAdd(@RequestBody Cart cart) {
        //TODO
        return ResponseResult.buildSuccess();
    }

    @Transactional
    @GetMapping("/list")
    @ApiOperation(value = "获取当前用户的购物车列表", notes = "获取当前用户的购物车列表", response = CartVO.class)
    public ResponseResult<List<CartVO>> cartList() {
        //TODO
        return ResponseResult.buildSuccess();
    }

    @Transactional
    @PutMapping("/update/quantity")
    @ApiOperation(value = "更新购物车商品数量", notes = "更新购物车商品数量", response = Boolean.class)
    public ResponseResult<Boolean> updateQuantity(@RequestBody CartUpdateQuantityQuery query) {
        //TODO
        return ResponseResult.buildSuccess();
    }

    @Transactional
    @DeleteMapping("/delete")
    @ApiOperation(value = "批量删除购物车中的商品", notes = "批量删除购物车中的商品", response = ResponseResult.class)
    public ResponseResult<Boolean> cartDelete(@RequestBody List<Long> cartIds) {
        //TODO
        return ResponseResult.buildSuccess();
    }

    @Transactional
    @DeleteMapping("/clear")
    @ApiOperation(value = "清空当前用户的购物车", notes = "清空当前用户的购物车", response = ResponseResult.class)
    public ResponseResult<Boolean> cartClear() {
        //TODO
        return ResponseResult.buildSuccess();
    }

}

