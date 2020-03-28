package com.whoiszxl.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.whoiszxl.order.entity.OrderItem;
import com.whoiszxl.product.entity.Sku;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 商品表 Mapper 接口
 * </p>
 *
 * @author author
 * @since 2020-03-20
 */
@Repository
public interface SkuMapper extends BaseMapper<Sku> {


    //扣减库存并增加销量
    @Update("update mall_sku set num=num-#{num},sale_num=sale_num+#{num} where id=#{skuId} and num>=#{num}")
    int decrCount(OrderItem orderItem);

    //回滚库存(增加库存并扣减销量)
    @Update("update mall_sku set num=num+#{num},sale_num=sale_num-#{num} where id=#{skuId}")
    void resumeStockNum(@Param("skuId") String skuId, @Param("num")Integer num);
}
