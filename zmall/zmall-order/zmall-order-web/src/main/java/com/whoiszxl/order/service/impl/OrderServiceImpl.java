package com.whoiszxl.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.common.constant.*;
import com.whoiszxl.common.utils.IdWorker;
import com.whoiszxl.order.entity.Order;
import com.whoiszxl.order.entity.OrderItem;
import com.whoiszxl.order.mapper.OrderItemMapper;
import com.whoiszxl.order.mapper.OrderMapper;
import com.whoiszxl.order.service.CartService;
import com.whoiszxl.order.service.OrderService;
import com.whoiszxl.product.feign.SkuFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author author
 * @since 2020-03-26
 */
@Slf4j
@Service
@Transactional
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private CartService cartService;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Resource
    private SkuFeign skuFeign;

    @Override
    public boolean addOrder(Order order) {
        //1. 获取购物车商品
        Map cartData = cartService.list(order.getUsername());
        List<OrderItem> orderItemList = (List<OrderItem>)cartData.get("orderItemList");
        if(orderItemList.size() <= 0) {
            throw new RuntimeException("购物车没有商品");
        }

        order.setTotalNum((Integer) cartData.get("totalNum"));
        order.setTotalMoney((Integer) cartData.get("totalMoney"));
        order.setUpdateTime(order.getCreateTime());
        order.setBuyerRate(BooleanEnum.IS_FALSE.getBool());
        order.setSourceType(SourceTypeEnum.APP.getSource());
        order.setOrderStatus(OrderStatusEnum.NOT_COMPLETE.getStatus());
        order.setPayStatus(PayStatusEnum.NOT_PAY.getStatus());
        order.setConsignStatus(ConsignStatusEnum.NOT_SEND.getStatus());
        order.setId(idWorker.nextId() + "");
        orderMapper.insert(order);

        //添加订单明细
        for (OrderItem orderItem : orderItemList) {
            orderItem.setId(idWorker.nextId() + "");
            orderItem.setIsReturn(BooleanEnum.IS_FALSE.getBool());
            orderItem.setOrderId(order.getId());
            orderItemMapper.insert(orderItem);
        }

        //扣减库存
        skuFeign.decrCount(order.getUsername());

        redisTemplate.delete("cart_" + order.getUsername());
        return true;
    }
}
