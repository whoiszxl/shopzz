package com.whoiszxl.stock;

/**
 * 库存更新命令的接口
 *
 * @author whoiszxl
 * @date 2021/7/21
 */
public interface StockUpdater {

    /**
     * 更新商品库存
     * @return 处理结果
     */
    Boolean updateProductStock();
}
