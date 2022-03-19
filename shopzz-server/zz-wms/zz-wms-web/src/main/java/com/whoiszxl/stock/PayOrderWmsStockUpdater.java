package com.whoiszxl.stock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Scope("prototype")
public class PayOrderWmsStockUpdater extends AbstractWmsStockUpdater {


    @Override
    protected void updateProductStock() {
        //TODO 支付订单成功后更新商品库存
        log.info("支付订单成功后更新商品库存");
    }


    @Override
    public void setParameter(Object parameter) {

    }
}
