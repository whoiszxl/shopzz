package com.whoiszxl.stock;

import com.whoiszxl.constants.StockStatus;
import com.whoiszxl.entity.ProductStock;
import com.whoiszxl.service.ProductStockService;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * 库存更新工厂基类
 *
 * @author whoiszxl
 * @date 2021/7/21
 */
@Slf4j
public abstract class AbstractStockUpdaterFactory<T> implements StockUpdaterFactory<T> {

    protected ProductStockService productStockService;

    public AbstractStockUpdaterFactory(ProductStockService productStockService) {
        this.productStockService = productStockService;
    }

    /**
     * 创建库存更新命令
     * @param parameter 参数对象
     * @return
     */
    @Override
    public StockUpdater create(T parameter) {
        //1. 从传入的参数中拿到所有的skuId
        List<Long> productSkuIds = getProductSkuIds(parameter);

        //2. 通过所有的skuId创建出商品库存对象集合来
        List<ProductStock> productStocks = createProductStocks(productSkuIds);
        return createCommand(parameter);
    }

    /**
     * 获取商品skuid集合
     * @param parameter 参数
     * @return 商品sku id集合
     */
    protected abstract List<Long> getProductSkuIds(T parameter);

    /**
     * 创建库存更新命令
     * @param productStocks 商品库存对象集合
     * @param parameter 参数
     * @return 库存更新命令
     */
    protected abstract StockUpdater createCommand(T parameter);

    /**
     * 创建商品库存DO对象集合
     * @param productSkuIds 商品sku id集合
     * @return 商品库存DO对象集合
     */
    private List<ProductStock> createProductStocks(List<Long> productSkuIds) {
        List<ProductStock> productStocks = new ArrayList<>(productSkuIds.size());
        for(Long skuId : productSkuIds) {
            ProductStock productStock = productStockService.getProductStockBySkuId(skuId);
            if(productStock == null) {
                productStock = createProductStock(skuId);
                productStockService.save(productStock);
            }
            productStocks.add(productStock);
        }

        return productStocks;
    }

    /**
     * 创建商品库存对象
     * @param productSkuId 商品sku id
     * @return 商品库存对象
     */
    private ProductStock createProductStock(Long productSkuId) {
        ProductStock productStock = new ProductStock();
        productStock.setProductSkuId(productSkuId);
        productStock.setSaleStockQuantity(0);
        productStock.setLockedStockQuantity(0);
        productStock.setSaledStockQuantity(0);
        productStock.setStockStatus(StockStatus.NOT_IN_STOCK);
        return productStock;
    }
}
