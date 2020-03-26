package com.whoiszxl.order.service.impl;

import com.whoiszxl.order.entity.OrderItem;
import com.whoiszxl.order.service.CartService;
import com.whoiszxl.product.entity.Sku;
import com.whoiszxl.product.entity.Spu;
import com.whoiszxl.product.feign.SkuFeign;
import com.whoiszxl.product.feign.SpuFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: whoiszxl
 * @create: 2020-03-26
 **/
@Service
public class CartServiceImpl implements CartService {

    private static final String CART="cart_";

    @Autowired
    private RedisTemplate redisTemplate;

    @Resource
    private SkuFeign skuFeign;

    @Resource
    private SpuFeign spuFeign;


    @Override
    public void addCart(String skuId, Integer num, String username) {
        //1. 查询redis中此用户的购物车商品信息
        OrderItem orderItem = (OrderItem) redisTemplate.boundHashOps(CART + username).get(skuId);
        if(orderItem != null) {
            //2. 购物车已存在该商品，更新数量与价钱
            orderItem.setNum(orderItem.getNum() + num);
            //3. 数量小于等于0，移除
            if(orderItem.getNum() <= 0) {
                redisTemplate.boundHashOps(CART + username).delete(skuId);
                return ;
            }
            orderItem.setMoney(orderItem.getNum()*orderItem.getPrice());
            orderItem.setPayMoney(orderItem.getNum()*orderItem.getPrice());
        }else {
            //4. redis不存在，第一次添加
            Sku sku = skuFeign.findById(skuId).getResult();
            Spu spu = spuFeign.findSpuById(sku.getSpuId()).getResult();

            //封装orderItem
            orderItem = this.sku2OrderItem(sku, spu, num);
        }
        //5. 将orderItem添加到redis中
        redisTemplate.boundHashOps(CART + username).put(skuId, orderItem);
    }

    @Override
    public Map list(String username) {
        Map<String, Object> map = new HashMap<>();

        List<OrderItem> orderItemList = redisTemplate.boundHashOps(CART + username).values();
        map.put("orderItemList", orderItemList);

        //商品的总数量与总价格
        Integer totalNum = 0;
        Integer totalMoney = 0;

        assert orderItemList != null;
        for (OrderItem orderItem : orderItemList) {
            totalNum += orderItem.getNum();
            totalMoney += orderItem.getMoney();
        }

        map.put("totalNum", totalNum);
        map.put("totalMoney", totalMoney);

        return map;
    }


    private OrderItem sku2OrderItem(Sku sku, Spu spu, Integer num) {
        OrderItem orderItem = new OrderItem();
        orderItem.setSpuId(sku.getSpuId());
        orderItem.setSkuId(sku.getId());
        orderItem.setName(sku.getName());
        orderItem.setPrice(sku.getPrice());
        orderItem.setNum(num);
        orderItem.setMoney(orderItem.getPrice() * num);
        orderItem.setPayMoney(orderItem.getPrice() * num);
        orderItem.setImage(sku.getImage());
        orderItem.setWeight(sku.getWeight() * num);
        //分类信息
        orderItem.setCategoryId1(spu.getCategory1Id());
        orderItem.setCategoryId2(spu.getCategory2Id());
        orderItem.setCategoryId3(spu.getCategory3Id());
        return orderItem;
    }
}
