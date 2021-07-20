package com.whoiszxl.stock;

/**
 * 库存更新组件抽象基类
 */
public abstract class AbstractWmsStockUpdater implements WmsStockUpdater {

    @Override
    public Boolean update() {
        updateProductStock();
        updateProductAllocationStock();
        updateProductAllocationStockDetail();
        return true;
    }

    /**
     * 更新商品库存
     */
    protected abstract void updateProductStock();

    /**
     * 更新货位库存
     */
    protected abstract void updateProductAllocationStock();

    /**
     * 更新货位库存明细
     * @throws Exception
     */
    protected abstract void updateProductAllocationStockDetail();

}
