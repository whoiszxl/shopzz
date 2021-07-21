package com.whoiszxl.service;

import com.whoiszxl.entity.DispatchProductAllocationStock;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 调度中心货位库存表 服务类
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-20
 */
public interface DispatchProductAllocationStockService extends IService<DispatchProductAllocationStock> {

    /**
     * 通过货位ID和SKUID获取到调度中心货位库存对象，不存在则新建
     * @param productAllocationId 货位ID
     * @param productSkuId SKUID
     * @return 调度中心货位库存对象
     */
    DispatchProductAllocationStock getOrSave(Long productAllocationId, Long productSkuId);
}
