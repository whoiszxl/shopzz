package com.whoiszxl.feign;

import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.config.OAuth2FeignConfig;
import com.whoiszxl.dto.OrderInfoDTO;
import com.whoiszxl.dto.SkuDTO;
import com.whoiszxl.dto.SkuStockDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
     *
     * @param skuId
     * @return
     */
    @GetMapping("/getSkuInfoBySkuId/{skuId}")
    ResponseResult<SkuDTO> getSkuInfoBySkuId(@PathVariable Long skuId);


    /**
     * 通过skuId列表获取库存
     * @param skuIds skuId列表
     * @return 库存列表
     */
    @PostMapping("/getSaleStockQuantity")
    ResponseResult<List<SkuStockDTO>> getSaleStockQuantity(@RequestBody List<Long> skuIds);


    /**
     * 校验库存是否充足
     * @param skuIds skuId列表
     * @return 是否库存充足
     */
    @PostMapping("/checkStock")
    ResponseResult<Boolean> checkStock(@RequestBody List<Long> skuIds);


    /**
     * 支付成功后更新库存
     * @param orderInfo
     */
    @PostMapping("/paySuccessUpdateStock")
    ResponseResult paySuccessUpdateStock(@RequestBody OrderInfoDTO orderInfo);

}
