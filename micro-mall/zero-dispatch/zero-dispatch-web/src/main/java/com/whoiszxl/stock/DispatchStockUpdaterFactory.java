package com.whoiszxl.stock;

import com.whoiszxl.utils.SpringApplicationContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 调度中心库存更新工厂
 */
@Component
public class DispatchStockUpdaterFactory {

    @Autowired
    private SpringApplicationContextUtil springApplicationContextUtil;


}
