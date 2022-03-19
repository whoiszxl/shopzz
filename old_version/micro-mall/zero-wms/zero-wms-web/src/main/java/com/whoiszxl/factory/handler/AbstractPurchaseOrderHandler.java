package com.whoiszxl.factory.handler;

import com.whoiszxl.dto.PurchaseOrderDTO;
import com.whoiszxl.factory.PurchaseInboundOrderResult;
import lombok.Getter;
import lombok.Setter;

/**
 * 采购入库单handler的抽象类
 */
public abstract class AbstractPurchaseOrderHandler implements PurchaseOrderHandler {

    /**
     * 如果当前的handler处理成功了，则下一个执行此handler
     */
    @Getter
    @Setter
    protected PurchaseOrderHandler successor;

    /**
     * 执行处理逻辑，执行完成当前的逻辑，便判断是否有下一个执行逻辑，以及当前执行的配置是否指定需要继续执行。
     * @param purchaseInboundOrderDTO 采购入库单
     * @return 处理是否成功
     */
    @Override
    public Boolean execute(PurchaseOrderDTO purchaseOrderDTO) {
        PurchaseInboundOrderResult result = doExecute(purchaseOrderDTO);
        if(successor != null && result.getDoNext()) {
            return successor.execute(purchaseOrderDTO);
        }
        return result.getResult();
    }

    /**
     * 当前handler的执行入口
     * @param purchaseInboundOrderDTO 采购入库单DTO
     * @return 结果
     */
    protected abstract PurchaseInboundOrderResult doExecute(PurchaseOrderDTO purchaseOrderDTO);

}
