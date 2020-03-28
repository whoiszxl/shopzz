package com.whoiszxl.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.order.entity.OrderItem;
import com.whoiszxl.product.mapper.SkuMapper;
import com.whoiszxl.product.entity.Sku;
import com.whoiszxl.product.service.SkuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.Query;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品表 服务实现类
 * </p>
 *
 * @author author
 * @since 2020-03-20
 */
@Slf4j
@Service
@Transactional
public class SkuServiceImpl extends ServiceImpl<SkuMapper, Sku> implements SkuService {


    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<Sku> findList(Map<String, Object> searchMap) {
        QueryWrapper queryMapper = createQueryWrapper(searchMap);
        return skuMapper.selectList(queryMapper);
    }

    @Override
    public void decrCount(String username) {
        //1.获取购物车中的数据
        List<OrderItem> orderItemList = redisTemplate.boundHashOps("cart_" + username).values();

        //2.循环扣减库存并增加销量
        for (OrderItem orderItem : orderItemList) {
            int count = skuMapper.decrCount(orderItem);
            if(count <= 0) {
                throw new RuntimeException("库存不足");
            }
        }
    }

    /**
     * 构建查询包装器
     * @param searchMap 搜索条件
     * @return
     */
    private QueryWrapper createQueryWrapper(Map<String,Object> searchMap) {
        QueryWrapper queryWrapper = new QueryWrapper();
        if(searchMap != null) {
            // 商品id
            if(searchMap.get("id")!=null && !"".equals(searchMap.get("id"))){
                queryWrapper.eq("id", searchMap.get("id"));
            }
            // 商品条码
            if(searchMap.get("sn")!=null && !"".equals(searchMap.get("sn"))){
                queryWrapper.eq("sn",searchMap.get("sn"));
            }
            // SKU名称
            if(searchMap.get("name")!=null && !"".equals(searchMap.get("name"))){
                queryWrapper.like("name","%"+searchMap.get("name")+"%");
            }
            // 商品图片
            if(searchMap.get("image")!=null && !"".equals(searchMap.get("image"))){
                queryWrapper.like("image","%"+searchMap.get("image")+"%");
            }
            // 商品图片列表
            if(searchMap.get("images")!=null && !"".equals(searchMap.get("images"))){
                queryWrapper.like("images","%"+searchMap.get("images")+"%");
            }
            // SPUID
            if(searchMap.get("spuId")!=null && !"".equals(searchMap.get("spuId"))){
                queryWrapper.eq("spu_id",searchMap.get("spuId"));
            }
            // 类目名称
            if(searchMap.get("categoryName")!=null && !"".equals(searchMap.get("categoryName"))){
                queryWrapper.like("category_name","%"+searchMap.get("categoryName")+"%");
            }
            // 品牌名称
            if(searchMap.get("brandName")!=null && !"".equals(searchMap.get("brandName"))){
                queryWrapper.like("brand_name","%"+searchMap.get("brandName")+"%");
            }
            // 规格
            if(searchMap.get("spec")!=null && !"".equals(searchMap.get("spec"))){
                queryWrapper.like("spec","%"+searchMap.get("spec")+"%");
            }
            // 商品状态 1-正常，2-下架，3-删除
            if(searchMap.get("status")!=null && !"".equals(searchMap.get("status"))){
                queryWrapper.eq("status", searchMap.get("status"));
            }

            // 价格（分）
            if(searchMap.get("price")!=null ){
                queryWrapper.eq("price",searchMap.get("price"));
            }
            // 库存数量
            if(searchMap.get("num")!=null ){
                queryWrapper.eq("num",searchMap.get("num"));
            }
            // 库存预警数量
            if(searchMap.get("alertNum")!=null ){
                queryWrapper.eq("alert_num",searchMap.get("alertNum"));
            }
            // 重量（克）
            if(searchMap.get("weight")!=null ){
                queryWrapper.eq("weight",searchMap.get("weight"));
            }
            // 类目ID
            if(searchMap.get("categoryId")!=null ){
                queryWrapper.eq("category_id",searchMap.get("categoryId"));
            }
            // 销量
            if(searchMap.get("saleNum")!=null ){
                queryWrapper.eq("sale_num",searchMap.get("saleNum"));
            }
            // 评论数
            if(searchMap.get("commentNum")!=null ){
                queryWrapper.eq("comment_num",searchMap.get("commentNum"));
            }
        }

        return queryWrapper;
    }
}
