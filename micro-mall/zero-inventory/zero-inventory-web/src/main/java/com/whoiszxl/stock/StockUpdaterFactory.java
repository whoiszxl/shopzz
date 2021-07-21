package com.whoiszxl.stock;

/**
 * 库存更新组件工厂
 *
 * @author whoiszxl
 * @date 2021/7/21
 */
public interface StockUpdaterFactory<T> {

    /**
     * 创建一个库存更新命令
     * @param parameter 参数对象
     * @return 库存更新命令
     */
    StockUpdater create(T parameter);

}
