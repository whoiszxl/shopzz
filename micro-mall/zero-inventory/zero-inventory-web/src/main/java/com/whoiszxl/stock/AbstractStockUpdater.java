package com.whoiszxl.stock;

import com.whoiszxl.constants.StockStatus;
import com.whoiszxl.entity.ProductStock;
import com.whoiszxl.service.ProductStockService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 商品库存更新命令的抽象基类
 *
 * @author whoiszxl
 * @date 2021/7/21
 */
@Slf4j
public abstract class AbstractStockUpdater implements StockUpdater {

    protected List<ProductStock> productStocks;

    protected ProductStockService productStockService;

    public AbstractStockUpdater(List<ProductStock> productStocks, ProductStockService productStockService) {
        this.productStocks = productStocks;
        this.productStockService = productStockService;
    }

    @Override
    public Boolean updateProductStock() {
        try {
            updateSaleStockQuantity();
            updateLockedStockQuantity();
            updateSaledStockQuantity();
            updateStockStatus();
            executeUpdateProductStock();
        } catch (Exception e) {
            log.error("updateProductStock 更新商品库存发生异常", e);
        }
        return true;
    }

    /**
     * 更新商品的销售库存
     */
    protected abstract void updateSaleStockQuantity();

    /**
     * 更新商品的锁定库存
     */
    protected abstract void updateLockedStockQuantity();

    /**
     * 更新商品的已销售库存
     */
    protected abstract void updateSaledStockQuantity();

    /**
     * 更新商品的库存状态
     */
    private void updateStockStatus() {
        for(ProductStock productStock : productStocks) {
            if(productStock.getSaleStockQuantity() > 0L) {
                productStock.setStockStatus(StockStatus.IN_STOCK);
            } else {
                productStock.setStockStatus(StockStatus.NOT_IN_STOCK);
            }
        }
    }


    /**
     * 实际执行更新商品库存的操作
     * @throws Exception
     */
    private void executeUpdateProductStock() {
        productStockService.updateBatchById(productStocks);
    }
}
