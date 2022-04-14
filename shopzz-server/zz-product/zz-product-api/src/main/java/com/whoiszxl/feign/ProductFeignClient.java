package com.whoiszxl.feign;

import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.config.OAuth2FeignConfig;
import com.whoiszxl.dto.ReduceStockFeignDTO;
import com.whoiszxl.dto.SkuFeignDTO;
import com.whoiszxl.dto.SkuStockFeignDTO;
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
     * @param spuId spu id
     * @return
     */
    @GetMapping("/getSpuBySpuId/{skuId}")
    ResponseResult<SpuFeignDTO> getSpuBySpuId(@PathVariable Long spuId);

    /**
     * 根据spuId获取SPU详细信息
     * @param spuIds spu id 集合
     * @return
     */
    @GetMapping("/getSpuListBySpuIdList")
    ResponseResult<List<SpuFeignDTO>> getSpuListBySpuIdList(@RequestParam(name = "spuIds") String spuIds);


    /**
     * 根据spuId获取SPU详细信息
     * @param spuIds spu id 集合
     * @return
     */
    @GetMapping("/getSkuListBySkuIdList")
    ResponseResult<List<SkuFeignDTO>> getSkuListBySkuIdList(@RequestParam(name = "skuIds") String skuIds);


    /**
     * 通过skuId获取SKU详细信息
     * @param skuId skuID
     * @return
     */
    @GetMapping("/getSkuInfoBySkuId/{skuId}")
    ResponseResult<SkuFeignDTO> getSkuInfoBySkuId(@PathVariable Long skuId);

    /**
     * 通过skuCode获取SKU详细信息
     * @param skuCode sku编码
     * @return
     */
    @GetMapping("/getSkuInfoBySkuCode/{skuCode}")
    ResponseResult<SkuFeignDTO> getSkuInfoBySkuCode(@PathVariable String skuCode);


    @GetMapping("/getStockBySkuIdList")
    ResponseResult<List<SkuStockFeignDTO>> getStockBySkuIdList(@RequestParam(name = "skuIds") String skuIds);

    @PostMapping("/subSaleStockAndAddLockStockBySkuId")
    ResponseResult<Boolean> subSaleStockAndAddLockStockBySkuId(@RequestBody List<ReduceStockFeignDTO> reduceStockFeignList);
}
