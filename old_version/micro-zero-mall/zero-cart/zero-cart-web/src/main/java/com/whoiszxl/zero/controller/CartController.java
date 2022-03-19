package com.whoiszxl.zero.controller;

import com.whoiszxl.zero.bean.Result;
import com.whoiszxl.zero.dto.CartDTO;
import com.whoiszxl.zero.dto.CartListParam;
import com.whoiszxl.zero.entity.params.CartAddParam;
import com.whoiszxl.zero.entity.params.CartDeleteParam;
import com.whoiszxl.zero.entity.params.CartQuantityUpdateParam;
import com.whoiszxl.zero.entity.vo.CartVO;
import com.whoiszxl.zero.feign.CartFeign;
import com.whoiszxl.zero.service.CartService;
import com.whoiszxl.zero.utils.BeanCopierUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "购物车控制器")
@RestController
public class CartController implements CartFeign {

    @Autowired
    private CartService cartService;


    @ApiOperation("购物车列表")
    @GetMapping("/list")
    public Result<List<CartVO>> list() {
        List<CartDTO> cartDTOS = cartService.list();
        List<CartVO> cartVOS = BeanCopierUtils.copyListProperties(cartDTOS, CartVO::new);
        return Result.buildSuccess(cartVOS);
    }

    @ApiOperation("添加购物车")
    @PostMapping("/add")
    public Result add(@RequestBody CartAddParam cartAddParam) {
        int i = cartService.add(cartAddParam);
        return i == 0 ? Result.buildError() : Result.buildSuccess();
    }

    @ApiOperation("数量更新")
    @PutMapping("/quantity/update")
    public Result<CartVO> quantityUpdate(@RequestBody CartQuantityUpdateParam cartQuantityUpdateParam) {
        int i = cartService.quantityUpdate(cartQuantityUpdateParam);
        return i == 0 ? Result.buildError() : Result.buildSuccess();
    }

    @ApiOperation("购物车中删除商品")
    @PutMapping("/delete")
    public Result delete(@RequestBody CartDeleteParam cartDeleteParam) {
        int i = cartService.delete(cartDeleteParam);
        return i == 0 ? Result.buildError() : Result.buildSuccess();
    }

    @ApiOperation("购物车中删除所有商品")
    @PutMapping("/delete/all")
    public Result deleteAll() {
        int i = cartService.deleteAll();
        return i == 0 ? Result.buildError() : Result.buildSuccess();
    }


    @Override
    public Result<List<CartDTO>> cartCheckedList(CartListParam cartListParam) {
        List<CartDTO> cartDTOS = cartService.findAllCheckedCartByMemberId(cartListParam.getMemberId());
        return Result.buildSuccess(cartDTOS);
    }
}
