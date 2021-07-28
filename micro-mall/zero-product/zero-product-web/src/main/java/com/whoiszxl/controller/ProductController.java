package com.whoiszxl.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.constants.PriceSortConstants;
import com.whoiszxl.entity.Product;
import com.whoiszxl.entity.query.SearchQuery;
import com.whoiszxl.entity.vo.CustomProductDetailVO;
import com.whoiszxl.entity.vo.ProductDetailVO;
import com.whoiszxl.service.ProductService;
import com.whoiszxl.service.SkuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/product")
@Api(tags = "商品相关接口")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private SkuService skuService;

    @PostMapping("/detail/{productId}")
    @ApiOperation(value = "通过商品ID获取商品详情", notes = "通过商品ID获取商品详情", response = ProductDetailVO.class)
    public ResponseResult<CustomProductDetailVO> detail(@PathVariable Long productId) {
        CustomProductDetailVO customProductDetailVO = productService.detail(productId);
        return ResponseResult.buildSuccess(customProductDetailVO);
    }

    @PostMapping("/search")
    @ApiOperation(value = "商品数据库搜索", notes = "商品数据库搜索", response = ProductDetailVO.class)
    public ResponseResult<IPage> search(@RequestBody SearchQuery searchQuery) {
        LambdaQueryWrapper<Product> queryWrapper = new LambdaQueryWrapper();
        if(StringUtils.isNotBlank(searchQuery.getKeywords())) {
            queryWrapper.like(Product::getName, "%" + searchQuery.getKeywords() + "%");
        }

        if(searchQuery.getPriceSort() != null) {
            if (searchQuery.getPriceSort() == PriceSortConstants.ASC) {
                queryWrapper.orderByAsc(Product::getDefaultPrice);
            } else {
                queryWrapper.orderByDesc(Product::getDefaultPrice);
            }
        }
        IPage<Product> page = productService.page(new Page<>(searchQuery.getPage(), searchQuery.getSize()), queryWrapper);
        return ResponseResult.buildSuccess(page);
    }
}

