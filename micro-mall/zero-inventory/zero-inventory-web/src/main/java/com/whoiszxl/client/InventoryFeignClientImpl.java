package com.whoiszxl.client;

import com.whoiszxl.dto.PurchaseInboundOrderDTO;
import com.whoiszxl.feign.InventoryFeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 *
 * @author whoiszxl
 * @date 2021/7/21
 */
@RestController
public class InventoryFeignClientImpl implements InventoryFeignClient {

    @Override
    public Boolean notifyPurchaseInboundFinished(@RequestBody PurchaseInboundOrderDTO purchaseInboundOrderDTO) {
        //

        return null;
    }
}
