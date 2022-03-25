package com.whoiszxl.service;

import com.whoiszxl.cqrs.command.SkuSaveCommand;
import com.whoiszxl.entity.Sku;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * sku信息表 服务类
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-21
 */
public interface SkuService extends IService<Sku> {

    /**
     * 创建SKU
     * @param skuSaveCommand
     */
    void save(SkuSaveCommand skuSaveCommand);

}
