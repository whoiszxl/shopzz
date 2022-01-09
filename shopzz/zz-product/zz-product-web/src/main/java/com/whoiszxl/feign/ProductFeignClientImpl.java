package com.whoiszxl.feign;

import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.dozer.DozerUtils;
import com.whoiszxl.dto.SkuDTO;
import com.whoiszxl.entity.Sku;
import com.whoiszxl.service.SkuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ProductFeignClientImpl implements ProductFeignClient{

    @Autowired
    private SkuService skuService;

    @Autowired
    private DozerUtils dozerUtils;

    @Override
    public ResponseResult<SkuDTO> getSkuInfoBySkuId(Long skuId) {
        Sku sku = skuService.getById(skuId);
        SkuDTO skuDTO = dozerUtils.map(sku, SkuDTO.class);
        return ResponseResult.buildSuccess(skuDTO);
    }
}
