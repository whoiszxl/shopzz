package com.whoiszxl.service.impl;

import com.whoiszxl.entity.OrderItem;
import com.whoiszxl.mapper.OrderItemMapper;
import com.whoiszxl.service.OrderItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单明细表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2022-01-09
 */
@Service
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem> implements OrderItemService {

}
