package com.whoiszxl.taowu.feign;

import com.whoiszxl.taowu.common.entity.ResponseResult;
import com.whoiszxl.taowu.common.feign.FeignTokenConfig;
import com.whoiszxl.taowu.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品feign接口
 *
 * @author whoiszxl
 * @date 2021/7/30
 */
@FeignClient(name = "taowu-product-web", contextId = "productFeign", configuration = FeignTokenConfig.class)
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
     * @param skuIds sku id 集合
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

    /**
     * 通过sku id集合批量获取库存
     * @param skuIds
     * @return
     */
    @GetMapping("/getStockBySkuIdList")
    ResponseResult<List<SkuStockFeignDTO>> getStockBySkuIdList(@RequestParam(name = "skuIds") String skuIds);

    /**
     * 通过SKU ID扣除销售库存，增加锁定库存
     * @param reduceStockFeignList
     * @return
     */
    @PostMapping("/subSaleStockAndAddLockStockBySkuId")
    ResponseResult<Boolean> subSaleStockAndAddLockStockBySkuId(@RequestBody List<ReduceStockFeignDTO> reduceStockFeignList);

    /**
     * 支付成功后更新库存
     * @param orderInfo
     */
    @PostMapping("/paySuccessUpdateStock")
    ResponseResult paySuccessUpdateStock(@RequestBody OrderInfoDTO orderInfo);
}
