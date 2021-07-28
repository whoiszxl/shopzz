package com.whoiszxl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.dto.InventorySkuDTO;
import com.whoiszxl.entity.Product;
import com.whoiszxl.entity.ProductImages;
import com.whoiszxl.entity.Sku;
import com.whoiszxl.entity.vo.*;
import com.whoiszxl.feign.InventoryFeignClient;
import com.whoiszxl.mapper.ProductMapper;
import com.whoiszxl.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.utils.BeanCopierUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

/**
 * <p>
 * 商品基础信息表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-28
 */
@Slf4j
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Autowired
    private SkuService skuService;

    @Autowired
    private ProductImagesService productImagesService;

    @Autowired
    private SkuSaleAttributeValueService skuSaleAttributeValueService;

    @Autowired
    private ProductAttributeValueService productAttributeValueService;

    @Autowired
    private InventoryFeignClient inventoryFeignClient;

    @Autowired
    private ThreadPoolExecutor executor;

    @Override
    public CustomProductDetailVO detail(Long productId) {
        CustomProductDetailVO result = new CustomProductDetailVO();
        //获取SPU的基本信息
        CompletableFuture<Product> productInfoFuture = CompletableFuture.supplyAsync(() -> {
            Product product = this.getOne(new LambdaQueryWrapper<Product>().eq(Product::getId, productId));
            result.setProductVO(product.clone(ProductVO.class));
            return product;
        }, executor);

        //获取SPU下的SKU集合
        CompletableFuture<Void> skuVoFuture = CompletableFuture.runAsync(() -> {
            List<Sku> skuList = skuService.list(new LambdaQueryWrapper<Sku>().eq(Sku::getProductId, productId));
            List<SkuVO> skuVOS = BeanCopierUtils.copyListProperties(skuList, SkuVO::new);
            List<Long> skuIds = skuVOS.stream().map(SkuVO::getId).collect(Collectors.toList());
            ResponseResult<List<InventorySkuDTO>> saleStockQuantityResult = inventoryFeignClient.getSaleStockQuantity(skuIds);
            List<InventorySkuDTO> stockList = saleStockQuantityResult.getData();
            result.setSkus(skuVOS);
            result.setSkuStocks(stockList);
        }, executor);

        //获取SPU的图片列表
        CompletableFuture<Void> imagesFuture = CompletableFuture.runAsync(() -> {
            LambdaQueryWrapper<ProductImages> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ProductImages::getProductId, productId);
            queryWrapper.orderByDesc(ProductImages::getSort);
            List<ProductImages> imageList = productImagesService.list(queryWrapper);
            result.setImages(BeanCopierUtils.copyListProperties(imageList, ProductImagesVO::new));
        }, executor);

        //获取SPU的销售属性的多个组合
        CompletableFuture<Void> saleAttrFuture = CompletableFuture.runAsync(() -> {

            List<SkuDetailSaleAttributeVO> saleAttrs = skuSaleAttributeValueService.listSaleAttrs(productId);
            result.setSaleAttr(saleAttrs);

            //设置分组参数
            Map<String, List<SkuDetailSaleAttributeVO>> saleAttrsGroupMap = saleAttrs.stream().collect(Collectors.groupingBy(SkuDetailSaleAttributeVO::getAttributeName, Collectors.toList()));
            List<SaleAttrGroupVO> groupVOS = new ArrayList<>();
            for (List<SkuDetailSaleAttributeVO> value : saleAttrsGroupMap.values()) {
                groupVOS.add(new SaleAttrGroupVO(value.get(0).getAttributeId(), value.get(0).getAttributeName(), value));
            }
            result.setSaleAttrGroup(groupVOS);
        }, executor);

        //获取SPU的规格参数，依赖第一步获取的Product信息，需要里面的categoryId参数
        CompletableFuture<Void> attrGroupFuture = productInfoFuture.thenAcceptAsync(productInfo -> {

            List<SpuDetailAttrGroupVo> baseAttrs = productAttributeValueService.getProductGroupAttrsBySpuId(productInfo.getId(), productInfo.getCategoryId());
            result.setGroupAttrs(baseAttrs);
        }, executor);

        try {
            CompletableFuture.allOf(productInfoFuture, skuVoFuture, imagesFuture, saleAttrFuture, attrGroupFuture).get();
        } catch (Exception e) {
            log.error("获取商品详情线程池运行失败, 商品ID: " + productId, e);
        }
        return result;
    }
}
