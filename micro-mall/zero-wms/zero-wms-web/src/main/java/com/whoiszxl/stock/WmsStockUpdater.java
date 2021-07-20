package com.whoiszxl.stock;

/**
 * 商品库存更新命令的接口
 */
public interface WmsStockUpdater {

    /**
     * 更新商品库存
     * @return 是否更新成功
     */
    Boolean update();

    /**
     * 设置更新组件所需要的参数
     * @param parameter 参数
     */
    void setParameter(Object parameter);
}
