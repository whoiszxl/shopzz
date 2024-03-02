package com.whoiszxl.taowu.service;

import com.whoiszxl.taowu.entity.Currency;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 加密货币表 服务类
 * </p>
 *
 * @author whoiszxl
 * @since 2021-08-03
 */
public interface CurrencyService extends IService<Currency> {

    /**
     * 通过币种名称获取币种信息
     * @param currencyName 币种名称
     * @return 币种信息
     */
    Currency getCurrencyByName(String currencyName);

}
