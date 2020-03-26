package com.whoiszxl.product.feign;

import com.whoiszxl.common.entity.Result;
import com.whoiszxl.product.entity.Spu;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "zmall-product-web")
@RequestMapping("/product")
public interface SpuFeign {

    @GetMapping("/spu/findSpuById/{id}")
    public Result<Spu> findSpuById(@PathVariable("id") String id);
}

