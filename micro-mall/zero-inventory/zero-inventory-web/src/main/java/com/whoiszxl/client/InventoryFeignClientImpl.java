package com.whoiszxl.client;

import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.dto.PurchaseInboundOrderDTO;
import com.whoiszxl.feign.InventoryFeignClient;
import com.whoiszxl.stock.PurchaseInboundStockUpdaterFactory;
import com.whoiszxl.stock.StockUpdater;
import org.springframework.beans.factory.annotation.Autowired;
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

    /**
     * 采购入库库存更新命令
     */
    @Autowired
    private PurchaseInboundStockUpdaterFactory<PurchaseInboundOrderDTO> purchaseInboundStockUpdaterFactory;

    @Override
    public ResponseResult<Boolean> notifyPurchaseInboundFinished(@RequestBody PurchaseInboundOrderDTO purchaseInboundOrderDTO) {
        StockUpdater stockUpdater = purchaseInboundStockUpdaterFactory.create(purchaseInboundOrderDTO);
        stockUpdater.updateProductStock();
        return ResponseResult.buildSuccess();
    }
}
