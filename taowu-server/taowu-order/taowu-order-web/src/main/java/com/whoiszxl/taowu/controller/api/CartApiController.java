package com.whoiszxl.taowu.controller.api;


import cn.dev33.satoken.annotation.SaCheckLogin;
import com.whoiszxl.taowu.cqrs.command.CartAddCommand;
import com.whoiszxl.taowu.cqrs.command.CartDeleteCommand;
import com.whoiszxl.taowu.cqrs.command.CheckCartItemCommand;
import com.whoiszxl.taowu.cqrs.command.UpdateCartCommand;
import com.whoiszxl.taowu.cqrs.response.CartDetailApiResponse;
import com.whoiszxl.taowu.service.CartService;
import com.whoiszxl.taowu.common.entity.ResponseResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@Tag(name = "C端:购物车相关接口")
public class CartApiController {

    private final CartService cartService;


    @SaCheckLogin
    @PostMapping("/add")
    @Operation(summary = "添加购物车", description = "添加购物车")
    public ResponseResult<Boolean> addCart(@RequestBody CartAddCommand cartAddCommand) {
        cartService.addCart(cartAddCommand);
        return ResponseResult.buildSuccess();
    }

    @SaCheckLogin
    @GetMapping("/detail")
    @Operation(summary = "获取当前登录用户的购物车信息", description = "获取当前登录用户的购物车信息")
    public ResponseResult<CartDetailApiResponse> getCartDetail() {
        CartDetailApiResponse cartDetailApiResponse = cartService.getCartDetail();
        return ResponseResult.buildSuccess(cartDetailApiResponse);
    }

    @SaCheckLogin
    @PostMapping("/update/quantity")
    @Operation(summary = "更新购物车SKU数量", description = "更新购物车SKU数量")
    public ResponseResult<Boolean> updateQuantity(@RequestBody UpdateCartCommand updateCartCommand) {
        cartService.cartUpdate(updateCartCommand.getSkuId(), updateCartCommand.getQuantity());
        return ResponseResult.buildSuccess();
    }

    @SaCheckLogin
    @PostMapping("/delete")
    @Operation(summary = "删除购物车里的商品", description = "删除购物车里的商品")
    public ResponseResult<Boolean> cartDelete(@RequestBody CartDeleteCommand cartDeleteCommand) {
        Boolean deleteFlag = cartService.cartDelete(cartDeleteCommand.getSkuIdList());
        return ResponseResult.buildByFlag(deleteFlag);
    }


    @SaCheckLogin
    @PostMapping("/check")
    @Operation(summary = "选中或取消选中购物车里的商品", description = "选中或取消选中购物车里的商品")
    public ResponseResult<Boolean> checkOrUncheckCartItem(@RequestBody CheckCartItemCommand checkCartItemCommand) {
        Boolean checkFlag = cartService.checkOrUncheckCartItem(checkCartItemCommand.getIsChecked(), checkCartItemCommand.getSkuId());
        return ResponseResult.buildByFlag(checkFlag);
    }


    @SaCheckLogin
    @PostMapping("/clean")
    @Operation(summary = "清空当前登录用户的购物车", description = "清空当前登录用户的购物车")
    public ResponseResult<Boolean> cleanCart() {
        cartService.cleanCart();
        return ResponseResult.buildSuccess();
    }

}

