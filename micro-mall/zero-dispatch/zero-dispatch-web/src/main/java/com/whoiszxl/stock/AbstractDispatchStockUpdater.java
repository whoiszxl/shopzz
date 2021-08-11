package com.whoiszxl.stock;

/**
 * 调度中心库存更新抽象基类
 */
public abstract class AbstractDispatchStockUpdater implements DispatchStockUpdater {

    /**
     * 更新商品库存
     */
    @Override
    public Boolean update() {
        updateProductStock();
        updateProductAllocationStock();
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

}
