package com.whoiszxl.zero.controller;

import com.whoiszxl.zero.bean.BaseParam;
import com.whoiszxl.zero.bean.MyPage;
import com.whoiszxl.zero.bean.Result;
import com.whoiszxl.zero.dto.SkuDTO;
import com.whoiszxl.zero.entity.Sku;
import com.whoiszxl.zero.entity.params.SearchParams;
import com.whoiszxl.zero.entity.vo.ProductDetailVO;
import com.whoiszxl.zero.entity.vo.ProductVO;
import com.whoiszxl.zero.feign.ProductFeign;
import com.whoiszxl.zero.service.ProductService;
import com.whoiszxl.zero.service.SkuService;
import com.whoiszxl.zero.utils.BeanCopierUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "商品控制器")
@RestController
@RequestMapping("/product")
public class ProductController implements ProductFeign {

    @Autowired
    private ProductService productService;

    @Autowired
    private SkuService skuService;


    @ApiOperation(value = "商品详情")
    @PostMapping("/detail/{productId}")
    public Result<ProductDetailVO> detail(@PathVariable Long productId) {
        ProductDetailVO productDetailVO = productService.detail(productId);
        return Result.buildSuccess(productDetailVO);
    }

    @ApiOperation(value = "商品数据库搜索")
    @PostMapping("/search")
    public Result<MyPage> search(@RequestBody SearchParams searchParams) {
        MyPage result = productService.search(searchParams);
        List<ProductVO> productVOS = BeanCopierUtils.copyListProperties(result.getContent(), ProductVO::new);
        result.setContent(productVOS);
        return Result.buildSuccess(result);
    }


    @Override
    public SkuDTO skuInfo(BaseParam baseParam) {
        return skuService.findById(baseParam.getId());
    }
}
