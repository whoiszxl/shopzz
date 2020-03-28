package com.whoiszxl.product.feign;

import com.whoiszxl.common.entity.Result;
import com.whoiszxl.product.entity.Sku;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "zmall-product-web")
@RequestMapping("/product")
public interface SkuFeign {

    @PostMapping("/sku/spu/{spuId}")
    List<Sku> findSkuListBySpuId(@PathVariable("spuId") String spuId);

    @GetMapping("/sku/{id}")
    Result<Sku> findById(@PathVariable("id") String id);

    @PostMapping("/sku/decr/count")
    Result decrCount(@RequestParam("username") String username);

    @RequestMapping("/sku/resumeStockNum")
    Result resumeStockNum(@RequestParam("skuId") String skuId,@RequestParam("num")Integer num);
}
