package com.whoiszxl.zero.feign;

import com.whoiszxl.zero.bean.BaseParam;
import com.whoiszxl.zero.config.feign.OAuth2FeignConfig;
import com.whoiszxl.zero.dto.SkuDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "zero-product-web", contextId = "productFeign", configuration = OAuth2FeignConfig.class, path = "/product")
public interface ProductFeign {

    @PostMapping("/sku/info")
    SkuDTO skuInfo(@RequestBody BaseParam baseParam);
}
