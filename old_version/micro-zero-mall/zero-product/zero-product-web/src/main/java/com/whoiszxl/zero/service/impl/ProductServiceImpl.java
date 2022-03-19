package com.whoiszxl.zero.service.impl;

import com.whoiszxl.zero.bean.MyPage;
import com.whoiszxl.zero.dao.ProductDao;
import com.whoiszxl.zero.dao.ProductImagesDao;
import com.whoiszxl.zero.entity.Product;
import com.whoiszxl.zero.entity.ProductImages;
import com.whoiszxl.zero.dto.SkuDTO;
import com.whoiszxl.zero.entity.params.SearchParams;
import com.whoiszxl.zero.entity.vo.*;
import com.whoiszxl.zero.service.ProductAttributeValueService;
import com.whoiszxl.zero.service.ProductService;
import com.whoiszxl.zero.service.SkuSaleAttributeValueService;
import com.whoiszxl.zero.service.SkuService;
import com.whoiszxl.zero.utils.BeanCopierUtils;
import com.whoiszxl.zero.utils.JpaUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private SkuService skuService;

    @Autowired
    private ProductImagesDao productImagesDao;

    @Autowired
    private SkuSaleAttributeValueService skuSaleAttributeValueService;

    @Autowired
    private ProductAttributeValueService productAttributeValueService;

    @Autowired
    private ThreadPoolExecutor executor;

    @Override
    @Transactional
    public ProductDetailVO detail(Long productId) {
        ProductDetailVO result = new ProductDetailVO();
        //获取SPU的基本信息
        CompletableFuture<Product> productInfoFuture = CompletableFuture.supplyAsync(() -> {
            Product product = productDao.getOne(productId);
            result.setProductVO(product.clone(ProductVO.class));
            return product;
        }, executor);

        //获取SPU下的SKU集合
        CompletableFuture<Void> skuVoFuture = CompletableFuture.runAsync(() -> {
            List<SkuDTO> skuDTOS = skuService.findAllByProductId(productId);
            List<SkuVO> skuVOS = BeanCopierUtils.copyListProperties(skuDTOS, SkuVO::new);
            result.setSkus(skuVOS);
        }, executor);

        //获取SPU的图片列表
        CompletableFuture<Void> imagesFuture = CompletableFuture.runAsync(() -> {
            List<ProductImages> imageList = productImagesDao.findAllByProductIdOOrderBySortDesc(productId);
            result.setImages(BeanCopierUtils.copyListProperties(imageList, ProductImagesVO::new));
        }, executor);

        //获取SPU的销售属性的多个组合
        CompletableFuture<Void> saleAttrFuture = CompletableFuture.runAsync(() -> {

            List<SkuDetailSaleAttributeVO> saleAttrs = skuSaleAttributeValueService.listSaleAttrs(productId).stream().map(p -> {
                SkuDetailSaleAttributeVO vo = new SkuDetailSaleAttributeVO();
                vo.setAttributeId(p.getAttributeId());
                vo.setAttributeName(p.getAttributeName());
                vo.setAttributeValue(p.getAttributeValue());
                vo.setSkuIds(p.getSkuIds());
                return vo;
            }).collect(Collectors.toList());
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

            List<SpuDetailAttrGroupVo> baseAttrs = productAttributeValueService.getProductGroupAttrsBySpuId(productInfo.getId(), productInfo.getCategoryId()).stream().map(p -> {
                SpuDetailAttrGroupVo vo = new SpuDetailAttrGroupVo();
                vo.setAttributeId(p.getAttributeId());
                vo.setAttributeName(p.getAttributeName());
                vo.setAttributeValue(p.getAttributeValue());
                vo.setGroupName(p.getGroupName());
                return vo;
            }).collect(Collectors.toList());
            result.setGroupAttrs(baseAttrs);
        }, executor);

        try {
            CompletableFuture.allOf(productInfoFuture, skuVoFuture, imagesFuture, saleAttrFuture, attrGroupFuture).get();
        } catch (Exception e) {
            log.error("获取商品详情线程池运行失败, 商品ID: " + productId, e);
        }
        return result;
    }

    @Override
    public MyPage search(SearchParams searchParams) {
        Sort priceSort;
        if(searchParams.getPriceSort() != null) {
            priceSort = JpaUtils.getSort(searchParams.getPriceSort(), "defaultPrice");
        }else {
            priceSort = JpaUtils.getSort(-1, "createdAt");
        }

        Pageable pageable = JpaUtils.getPageable(searchParams.getPageNumber(), searchParams.getPageSize(), priceSort);

        Specification<Product> spec = getSpecification(searchParams);
        Page<Product> productPage = productDao.findAll(spec, pageable);
        return JpaUtils.convertMyPage(productPage);
    }

    /**
     * 组装查询条件
     * @param searchParams 查询条件
     * @return
     */
    private Specification<Product> getSpecification(SearchParams searchParams) {
        Specification<Product> specification = (Specification<Product>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(StringUtils.isNotBlank(searchParams.getKeywords())) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + searchParams.getKeywords() + "%"));
            }
            return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
        };

        return specification;
    }
}
