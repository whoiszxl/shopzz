package com.whoiszxl.feign;

import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.config.OAuth2FeignConfig;
import com.whoiszxl.dto.SkuDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 商品feign接口
 *
 * @author whoiszxl
 * @date 2021/7/30
 */
@FeignClient(name = "zz-product-web", contextId = "productFeign", configuration = OAuth2FeignConfig.class)
public interface ProductFeignClient {


    /**
     *
     * @param skuId
     * @return
     */
    @GetMapping("/getSkuInfoBySkuId/{skuId}")
    ResponseResult<SkuDTO> getSkuInfoBySkuId(@PathVariable Long skuId);
}
