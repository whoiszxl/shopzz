package com.whoiszxl.client;

import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.config.OAuth2FeignConfig;
import com.whoiszxl.dto.PurchaseOrderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 调度中心feign client
 *
 * @author whoiszxl
 * @date 2021/7/20
 */
@FeignClient(name = "zero-dispatch-web", contextId = "dispatchFeign", configuration = OAuth2FeignConfig.class, path = "/dispatch")
public interface DispatchClient {

    /**
     * 调度采购入库
     * @param purchaseOrderDTO 采购订单DTO
     * @return 是否调度成功
     */
    Boolean dispatchPurchaseInBound(@RequestBody PurchaseOrderDTO purchaseOrderDTO);

}
