package com.whoiszxl.controller;


import cn.dev33.satoken.annotation.SaCheckLogin;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.entity.query.DeleteCartQuery;
import com.whoiszxl.entity.query.SaveCartQuery;
import com.whoiszxl.entity.query.CheckCartItemQuery;
import com.whoiszxl.entity.vo.CartDetailVO;
import com.whoiszxl.service.CartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 购物车记录表 前端控制器
 * </p>
 *
 * @author whoiszxl
 * @since 2022-01-09
 */
@RestController
@RequestMapping("/cart")
@Api(tags = "购物车相关接口")
public class CartController {

    @Autowired
    private CartService cartService;


    @SaCheckLogin
    @PostMapping("/add")
    @ApiOperation(value = "添加购物车", notes = "添加购物车", response = Boolean.class)
    public ResponseResult<Boolean> addCart(@RequestBody SaveCartQuery saveCartQuery) {
        Boolean addFlag = cartService.cartAdd(saveCartQuery);
        return ResponseResult.buildByFlag(addFlag);
    }

    @SaCheckLogin
    @GetMapping("/detail")
    @ApiOperation(value = "获取当前登录用户的购物车信息", notes = "获取当前登录用户的购物车信息", response = CartDetailVO.class)
    public ResponseResult<CartDetailVO> getCartDetail() {
        CartDetailVO cartDetail = cartService.getCartDetail();
        return ResponseResult.buildSuccess(cartDetail);
    }

    @SaCheckLogin
    @PostMapping("/updateQuantity")
    @ApiOperation(value = "更新购物车SKU数量", notes = "更新购物车SKU数量", response = Boolean.class)
    public ResponseResult<CartDetailVO> updateQuantity(@RequestBody SaveCartQuery saveCartQuery) {
        Boolean updateFlag = cartService.cartUpdate(saveCartQuery.getSkuId(), saveCartQuery.getQuantity());
        return ResponseResult.buildByFlag(updateFlag);
    }

    @SaCheckLogin
    @PostMapping("/delete")
    @ApiOperation(value = "删除购物车里的商品", notes = "删除购物车里的商品", response = Boolean.class)
    public ResponseResult<Boolean> deleteCartItem(@RequestBody DeleteCartQuery deleteCartQuery) {
        Boolean deleteFlag = cartService.deleteCartItem(deleteCartQuery.getSkuIdList());
        return ResponseResult.buildByFlag(deleteFlag);
    }


    @SaCheckLogin
    @PostMapping("/check")
    @ApiOperation(value = "选中或取消选中购物车里的商品", notes = "选中或取消选中购物车里的商品", response = Boolean.class)
    public ResponseResult<Boolean> checkOrUncheckCartItem(@RequestBody CheckCartItemQuery checkCartItemVO) {
        Boolean checkFlag = cartService.checkOrUncheckCartItem(checkCartItemVO.getIsChecked(), checkCartItemVO.getSkuId());
        return ResponseResult.buildByFlag(checkFlag);
    }


    @SaCheckLogin
    @PostMapping("/clear")
    @ApiOperation(value = "清空当前登录用户的购物车", notes = "清空当前登录用户的购物车", response = Boolean.class)
    public ResponseResult<CartDetailVO> clearCart() {
        Boolean clearFlag = cartService.clearCart();
        return ResponseResult.buildByFlag(clearFlag);
    }


}

