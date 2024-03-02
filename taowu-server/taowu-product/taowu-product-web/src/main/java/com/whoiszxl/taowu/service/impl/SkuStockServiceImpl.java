package com.whoiszxl.taowu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.taowu.entity.SkuStock;
import com.whoiszxl.taowu.mapper.SkuStockMapper;
import com.whoiszxl.taowu.service.SkuStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品SKU库存表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-21
 */
@Service
@RequiredArgsConstructor
public class SkuStockServiceImpl extends ServiceImpl<SkuStockMapper, SkuStock> implements SkuStockService {

    private final SkuStockMapper skuStockMapper;

    @Override
    public int subSaleStockAndAddLockStockBySkuId(Long skuId, Integer quantity) {
        return skuStockMapper.subSaleStockAndAddLockStockBySkuId(skuId, quantity);
    }

    @Override
    public boolean subLockStockAndAddSaledStockBySkuId(Integer quantity, Long skuId) {
        return skuStockMapper.subLockStockAndAddSaledStockBySkuId(skuId, quantity);
    }
}
