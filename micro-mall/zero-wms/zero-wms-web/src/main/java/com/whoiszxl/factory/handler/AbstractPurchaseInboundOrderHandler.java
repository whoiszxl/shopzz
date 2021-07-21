package com.whoiszxl.factory.handler;

import com.whoiszxl.dto.PurchaseInboundOrderDTO;
import com.whoiszxl.factory.PurchaseInboundOrderResult;
import lombok.*;

/**
 * 采购入库单handler的抽象类
 */
public abstract class AbstractPurchaseInboundOrderHandler implements PurchaseInboundOrderHandler {

    /**
     * 如果当前的handler处理成功了，则下一个执行此handler
     */
    @Getter
    @Setter
    protected PurchaseInboundOrderHandler successor;

    /**
     * 执行处理逻辑，执行完成当前的逻辑，便判断是否有下一个执行逻辑，以及当前执行的配置是否指定需要继续执行。
     * @param purchaseInboundOrderDTO 采购入库单
     * @return 处理是否成功
     */
    @Override
    public Boolean execute(PurchaseInboundOrderDTO purchaseInboundOrderDTO) {
        PurchaseInboundOrderResult result = doExecute(purchaseInboundOrderDTO);
        if(successor != null && result.getDoNext()) {
            return successor.execute(purchaseInboundOrderDTO);
        }
        return result.getResult();
    }

    /**
     * 当前handler的执行入口
     * @param purchaseInboundOrderDTO 采购入库单DTO
     * @return 结果
     */
    protected abstract PurchaseInboundOrderResult doExecute(PurchaseInboundOrderDTO purchaseInboundOrderDTO);

}
