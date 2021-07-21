package com.whoiszxl.stock;

import com.whoiszxl.dto.PurchaseInboundOrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 调度中心 采购入库库存更新组件
 */
@Component
@Scope("prototype")
public class PurchaseInboundDispatchStockUpdater extends AbstractDispatchStockUpdater {

    @Autowired
    private PurchaseInboundOrderDTO purchaseInboundOrderDTO;


    @Override
    protected void updateProductStock() {

    }

    @Override
    protected void updateProductAllocationStock() {

    }

    @Override
    protected void updateProductAllocationStockDetail() {

    }

    @Override
    public void setParameter(Object parameter) {
        this.purchaseInboundOrderDTO = (PurchaseInboundOrderDTO) parameter;
    }
}
