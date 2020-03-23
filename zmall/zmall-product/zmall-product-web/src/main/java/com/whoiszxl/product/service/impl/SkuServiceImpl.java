package com.whoiszxl.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.product.mapper.SkuMapper;
import com.whoiszxl.product.entity.Sku;
import com.whoiszxl.product.service.SkuService;
import lombok.extern.slf4j.Slf4j;
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



    @Override
    public List<Sku> findList(Map<String, Object> searchMap) {
        QueryWrapper queryMapper = createQueryWrapper(searchMap);
        return null;
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
                queryWrapper.eq("spuId",searchMap.get("spuId"));
            }
            // 类目名称
            if(searchMap.get("categoryName")!=null && !"".equals(searchMap.get("categoryName"))){
                queryWrapper.like("categoryName","%"+searchMap.get("categoryName")+"%");
            }
            // 品牌名称
            if(searchMap.get("brandName")!=null && !"".equals(searchMap.get("brandName"))){
                queryWrapper.like("brandName","%"+searchMap.get("brandName")+"%");
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
                queryWrapper.eq("alertNum",searchMap.get("alertNum"));
            }
            // 重量（克）
            if(searchMap.get("weight")!=null ){
                queryWrapper.eq("weight",searchMap.get("weight"));
            }
            // 类目ID
            if(searchMap.get("categoryId")!=null ){
                queryWrapper.eq("categoryId",searchMap.get("categoryId"));
            }
            // 销量
            if(searchMap.get("saleNum")!=null ){
                queryWrapper.eq("saleNum",searchMap.get("saleNum"));
            }
            // 评论数
            if(searchMap.get("commentNum")!=null ){
                queryWrapper.eq("commentNum",searchMap.get("commentNum"));
            }
        }

        return queryWrapper;
    }
}
