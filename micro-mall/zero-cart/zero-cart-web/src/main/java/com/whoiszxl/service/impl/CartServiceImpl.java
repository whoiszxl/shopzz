package com.whoiszxl.service.impl;

import com.whoiszxl.entity.Cart;
import com.whoiszxl.mapper.CartMapper;
import com.whoiszxl.service.CartService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 购物车记录表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-30
 */
@Service
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements CartService {

}
