package com.whoiszxl.product.feign;

import com.whoiszxl.product.entity.Sku;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(name = "product")
@RequestMapping("/sku")
public interface SkuFeign {

    @GetMapping("/sku/spu/spuId")
    public List<Sku> findSkuListBySpuId(@PathVariable("spuId") String spuId);
}
