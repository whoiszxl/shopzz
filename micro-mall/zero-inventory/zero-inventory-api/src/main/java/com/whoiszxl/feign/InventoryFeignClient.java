package com.whoiszxl.feign;

import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.config.OAuth2FeignConfig;
import com.whoiszxl.dto.PurchaseInboundOrderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
}
