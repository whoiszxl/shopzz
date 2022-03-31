package com.whoiszxl.feign;

import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.config.OAuth2FeignConfig;
import com.whoiszxl.dto.SpuFeignDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品feign接口
 *
 * @author whoiszxl
 * @date 2021/7/30
 */
@FeignClient(name = "zz-product-web", contextId = "productFeign", configuration = OAuth2FeignConfig.class)
public interface ProductFeignClient {


    /**
     * 根据spuId获取SPU详细信息
     * @param spuId
     * @return
     */
    @GetMapping("/getSpuBySpuId/{skuId}")
    ResponseResult<SpuFeignDTO> getSpuBySpuId(@PathVariable Long spuId);

    /**
     * 根据spuId获取SPU详细信息
     * @param spuId
     * @return
     */
    @GetMapping("/getSpuListBySpuIdList")
    ResponseResult<List<SpuFeignDTO>> getSpuListBySpuIdList(@RequestParam(name = "spuIds") String spuIds);


}
