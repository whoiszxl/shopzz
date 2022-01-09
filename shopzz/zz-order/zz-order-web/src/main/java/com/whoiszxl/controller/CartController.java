package com.whoiszxl.controller;


import cn.dev33.satoken.annotation.SaCheckLogin;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.entity.query.AddCartQuery;
import com.whoiszxl.service.CartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseResult<Boolean> addCart(@RequestBody AddCartQuery addCartQuery) {
        Boolean addFlag = cartService.cartAdd(addCartQuery);
        return ResponseResult.buildByFlag(addFlag);
    }


}

