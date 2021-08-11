package com.whoiszxl.feign;

import com.whoiszxl.dto.*;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.config.OAuth2FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 库存中心 库存对外接口
 *
 * @author whoiszxl
 * @date 2021/7/21
 */
@FeignClient(name = "zero-inventory-web", contextId = "inventoryFeign", configuration = OAuth2FeignConfig.class)
public interface InventoryFeignClient {


    /**
     * 通知库存中心采购入库已经完成了
     * @param purchaseInboundOrderDTO 采购入库订单
     * @return 是否处理成功
     */
    @PostMapping("/notifyPurchaseInboundFinished")
    ResponseResult<Boolean> notifyPurchaseInboundFinished(@RequestBody PurchaseInboundOrderDTO purchaseInboundOrderDTO);

    /**
     * 通过skuId列表获取库存
     * @param skuIds skuId列表
     * @return 库存列表
     */
    @PostMapping("/getSaleStockQuantity")
    ResponseResult<List<InventorySkuDTO>> getSaleStockQuantity(@RequestBody List<Long> skuIds);

    /**
     * 通知库存中心提交订单的事件发生了
     * @param orderCreateInfoDTO 订单创建信息DTO
     * @return
     */
    @PostMapping("/notifySubmitOrderEvent")
    ResponseResult notifySubmitOrderEvent(@RequestBody OrderCreateInfoDTO orderCreateInfoDTO);

    /**
     * 通知库存中心支付订单事件完成了
     * @param orderInfo 订单详细数据
     * @return
     */
    @PostMapping("/notifyPayOrderEvent")
    ResponseResult notifyPayOrderEvent(@RequestBody OrderInfoDTO orderInfo);
}
