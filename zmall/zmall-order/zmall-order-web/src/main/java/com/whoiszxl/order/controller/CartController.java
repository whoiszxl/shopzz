package com.whoiszxl.order.controller;

import com.whoiszxl.common.entity.Result;
import com.whoiszxl.order.service.CartService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description:
 * @author: whoiszxl
 * @create: 2020-03-26
 **/
@Api(value = "ZMALL-购物车管理控制器", tags = "ZMALL-购物车管理控制器")
@RestController
@CrossOrigin
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;


    @GetMapping("/add")
    public Result add(@RequestParam("skuId") String skuId, @RequestParam("num") Integer num) {
        String username = "wanjiu";

        cartService.addCart(skuId, num, username);
        return Result.success();
    }

}


