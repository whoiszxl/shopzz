package com.whoiszxl.controller.api;


import cn.dev33.satoken.annotation.SaCheckLogin;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.cqrs.command.CartAddCommand;
import com.whoiszxl.cqrs.command.CartDeleteCommand;
import com.whoiszxl.cqrs.command.CheckCartItemCommand;
import com.whoiszxl.cqrs.command.UpdateCartCommand;
import com.whoiszxl.cqrs.response.CartDetailApiResponse;
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
 * @since 2022-04-09
 */
@RestController
@RequestMapping("/api/cart")
@Api(tags = "C端:购物车相关接口")
public class CartApiController {

    @Autowired
    private CartService cartService;


    @SaCheckLogin
    @PostMapping("/add")
    @ApiOperation(value = "添加购物车", notes = "添加购物车", response = Boolean.class)
    public ResponseResult<Boolean> addCart(@RequestBody CartAddCommand cartAddCommand) {
        cartService.addCart(cartAddCommand);
        return ResponseResult.buildSuccess();
    }

    @SaCheckLogin
    @GetMapping("/detail")
    @ApiOperation(value = "获取当前登录用户的购物车信息", notes = "获取当前登录用户的购物车信息", response = CartDetailApiResponse.class)
    public ResponseResult<CartDetailApiResponse> getCartDetail() {
        CartDetailApiResponse cartDetailApiResponse = cartService.getCartDetail();
        return ResponseResult.buildSuccess(cartDetailApiResponse);
    }

    @SaCheckLogin
    @PostMapping("/update/quantity")
    @ApiOperation(value = "更新购物车SKU数量", notes = "更新购物车SKU数量", response = Boolean.class)
    public ResponseResult<Boolean> updateQuantity(@RequestBody UpdateCartCommand updateCartCommand) {
        cartService.cartUpdate(updateCartCommand.getSkuId(), updateCartCommand.getQuantity());
        return ResponseResult.buildSuccess();
    }

    @SaCheckLogin
    @PostMapping("/delete")
    @ApiOperation(value = "删除购物车里的商品", notes = "删除购物车里的商品", response = Boolean.class)
    public ResponseResult<Boolean> cartDelete(@RequestBody CartDeleteCommand cartDeleteCommand) {
        Boolean deleteFlag = cartService.cartDelete(cartDeleteCommand.getSkuIdList());
        return ResponseResult.buildByFlag(deleteFlag);
    }


    @SaCheckLogin
    @PostMapping("/check")
    @ApiOperation(value = "选中或取消选中购物车里的商品", notes = "选中或取消选中购物车里的商品", response = Boolean.class)
    public ResponseResult<Boolean> checkOrUncheckCartItem(@RequestBody CheckCartItemCommand checkCartItemCommand) {
        Boolean checkFlag = cartService.checkOrUncheckCartItem(checkCartItemCommand.getIsChecked(), checkCartItemCommand.getSkuId());
        return ResponseResult.buildByFlag(checkFlag);
    }


    @SaCheckLogin
    @PostMapping("/clean")
    @ApiOperation(value = "清空当前登录用户的购物车", notes = "清空当前登录用户的购物车", response = Boolean.class)
    public ResponseResult<Boolean> cleanCart() {
        cartService.cleanCart();
        return ResponseResult.buildSuccess();
    }

}

