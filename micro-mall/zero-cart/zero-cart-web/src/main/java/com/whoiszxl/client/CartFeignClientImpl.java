package com.whoiszxl.client;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.constants.CartCheckConstants;
import com.whoiszxl.dto.CartDTO;
import com.whoiszxl.entity.Cart;
import com.whoiszxl.entity.vo.CartVO;
import com.whoiszxl.feign.CartFeignClient;
import com.whoiszxl.service.CartService;
import com.whoiszxl.utils.BeanCopierUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 购物车feign接口实现
 *
 * @author whoiszxl
 * @date 2021/7/30
 */
@Slf4j
@RestController
public class CartFeignClientImpl implements CartFeignClient {

    @Autowired
    private CartService cartService;

    @Override
    public ResponseResult<List<CartDTO>> getCheckedCartItem() {
        long memberId = StpUtil.getLoginIdAsLong();
        LambdaQueryWrapper<Cart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Cart::getMemberId, memberId);
        queryWrapper.eq(Cart::getChecked, CartCheckConstants.CHECKED);
        List<Cart> cartList = cartService.list(queryWrapper);
        List<CartDTO> cartDTOList = BeanCopierUtils.copyListProperties(cartList, CartDTO::new);
        return ResponseResult.buildSuccess(cartDTOList);
    }
}
