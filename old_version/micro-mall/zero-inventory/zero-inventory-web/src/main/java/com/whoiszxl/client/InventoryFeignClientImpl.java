package com.whoiszxl.client;

import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.dto.InventorySkuDTO;
import com.whoiszxl.dto.OrderCreateInfoDTO;
import com.whoiszxl.dto.OrderInfoDTO;
import com.whoiszxl.dto.PurchaseOrderDTO;
import com.whoiszxl.entity.ProductStock;
import com.whoiszxl.feign.InventoryFeignClient;
import com.whoiszxl.service.ProductStockService;
import com.whoiszxl.stock.PayOrderStockUpdaterFactory;
import com.whoiszxl.stock.PurchaseStockUpdaterFactory;
import com.whoiszxl.stock.StockUpdater;
import com.whoiszxl.stock.SubmitOrderStockUpdaterFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

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
    private PurchaseStockUpdaterFactory<PurchaseOrderDTO> purchaseStockUpdaterFactory;

    @Autowired
    private ProductStockService productStockService;

    @Autowired
    private SubmitOrderStockUpdaterFactory<OrderCreateInfoDTO> submitOrderStockUpdaterFactory;

    @Autowired
    private PayOrderStockUpdaterFactory<OrderInfoDTO> payOrderStockUpdaterFactory;

    @Override
    public ResponseResult<Boolean> notifyPurchaseOrderFinished(@RequestBody PurchaseOrderDTO purchaseOrderDTO) {
        StockUpdater stockUpdater = purchaseStockUpdaterFactory.createCommand(purchaseOrderDTO);
        Boolean updateFlag = stockUpdater.updateProductStock();
        return ResponseResult.buildByFlag(updateFlag);
    }

    @Override
    public ResponseResult<List<InventorySkuDTO>> getSaleStockQuantity(List<Long> skuIds) {
        List<InventorySkuDTO> results = new ArrayList<>();
        for (Long skuId : skuIds) {
            ProductStock productStock = productStockService.getProductStockBySkuId(skuId);
            if(productStock == null) {
                results.add(new InventorySkuDTO(skuId, 0));
            }else {
                results.add(new InventorySkuDTO(skuId, productStock.getSaleStockQuantity()));
            }
        }
        return ResponseResult.buildSuccess(results);
    }


    @Override
    public ResponseResult<Boolean> notifySubmitOrderEvent(OrderCreateInfoDTO orderCreateInfoDTO) {
        //更新库存中心库存
        StockUpdater stockUpdater = submitOrderStockUpdaterFactory.createCommand(orderCreateInfoDTO);
        Boolean updateFlag = stockUpdater.updateProductStock();

        return ResponseResult.buildByFlag(updateFlag);
    }

    @Override
    public ResponseResult notifyPayOrderEvent(OrderInfoDTO orderInfo) {
        //使用支付订单库存更新工厂创建出对应的命令来进行更新
        StockUpdater stockUpdater = payOrderStockUpdaterFactory.create(orderInfo);
        Boolean updateFlag = stockUpdater.updateProductStock();
        return ResponseResult.buildByFlag(updateFlag);
    }
}
