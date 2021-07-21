package com.whoiszxl.stock;

/**
 * 调度中心库存更新接口
 */
public interface DispatchStockUpdater {

    /**
     * 更新商品库存
     * @return 处理结果
     */
    Boolean update();

    /**
     * 设置这个更新组件需要的参数
     * @param parameter 参数
     */
    void setParameter(Object parameter);
}
