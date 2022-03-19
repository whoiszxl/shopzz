package com.whoiszxl.stock;

import com.whoiszxl.service.ProductStockService;
import lombok.extern.slf4j.Slf4j;

/**
 * 商品库存更新命令的抽象基类
 *
 * @author whoiszxl
 * @date 2021/7/21
 */
@Slf4j
public abstract class AbstractStockUpdater implements StockUpdater {

    protected ProductStockService productStockService;

    public AbstractStockUpdater(ProductStockService productStockService) {
        this.productStockService = productStockService;
    }

    @Override
    public Boolean updateProductStock() {
        try {
            //更新商品的销售库存与锁定库存
            boolean b = updateStock();
            if(!b) {
                return false;
            }

            //更新库存状态
            updateStockStatus();

        } catch (Exception e) {
            log.error("updateProductStock 更新商品库存发生异常", e);
        }
        return true;
    }

    /**
     * 更新商品的销售库存与锁定库存
     */
    protected abstract boolean updateStock();

    /**
     * 更新商品的库存状态
     */
    protected abstract boolean updateStockStatus();

}
