package com.whoiszxl.taowu.service;

import com.whoiszxl.taowu.entity.Height;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 区块高度同步记录 服务类
 * </p>
 *
 * @author whoiszxl
 * @since 2021-08-03
 */
public interface HeightService extends IService<Height> {

    /**
     * 通过币种名称获取币种链上高度
     * @param currencyName 币种名称
     * @return 币种高度
     */
    Height getHeightByCurrencyName(String currencyName);
}
