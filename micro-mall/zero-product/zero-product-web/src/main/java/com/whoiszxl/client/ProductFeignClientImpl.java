package com.whoiszxl.client;

import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.dto.SkuDTO;
import com.whoiszxl.entity.Sku;
import com.whoiszxl.feign.ProductFeignClient;
import com.whoiszxl.service.SkuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商品feign接口实现
 *
 * @author whoiszxl
 * @date 2021/7/30
 */
@Slf4j
@RestController
public class ProductFeignClientImpl implements ProductFeignClient {

    @Autowired
    private SkuService skuService;

    @Override
    public ResponseResult<SkuDTO> getSkuInfoBySkuId(Long skuId) {
        Sku sku = skuService.getById(skuId);
        return ResponseResult.buildSuccess(sku.clone(SkuDTO.class));
    }
}
