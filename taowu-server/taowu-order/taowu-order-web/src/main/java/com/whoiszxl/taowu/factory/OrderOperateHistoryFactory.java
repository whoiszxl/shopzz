package com.whoiszxl.taowu.factory;

import com.whoiszxl.taowu.constants.OrderOperateTypeConstants;
import com.whoiszxl.taowu.common.utils.IdWorker;
import com.whoiszxl.taowu.entity.Order;
import com.whoiszxl.taowu.entity.OrderOperateHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 订单操作历史记录工厂，根据订单状态创建对应的入库实体对象
 */
@Component
@RequiredArgsConstructor
public class OrderOperateHistoryFactory {

    private final IdWorker idWorker;

    /**
     * 通过对应的操作类型创建对应的实体对象
     * @param order 订单
     * @param operateType 操作类型
     * @return 订单操作记录实体
     */
    public OrderOperateHistory get(Order order, Integer operateType) {
        String operateNote = null;
        switch (operateType) {
            case OrderOperateTypeConstants.CREATE_ORDER:
                operateNote = String.format("完成创建订单，订单编号为：%s", order.getOrderNo());
                break;
            case OrderOperateTypeConstants.CANCEL_ORDER:
                operateNote = String.format("用户取消订单，订单编号为：%s", order.getOrderNo());
                break;
            case OrderOperateTypeConstants.PAY_ORDER:
                operateNote = String.format("用户支付了订单，订单编号为：%s，支付金额为：%s", order.getOrderNo(), order.getFinalPayAmount().toPlainString());
                break;
            case OrderOperateTypeConstants.PRODUCT_DELIVERY:
                operateNote = String.format("订单发货了，订单编号为：%s", order.getOrderNo());
                break;
            case OrderOperateTypeConstants.CONFIRM_RECEIPT:
                operateNote = String.format("用户确认收货了，订单编号为：%s", order.getOrderNo());
                break;
            default:
                break;
        }

        OrderOperateHistory history = new OrderOperateHistory();
        history.setId(idWorker.nextId());
        history.setOrderId(order.getId());
        history.setOperateType(operateType);
        history.setOperateNote(operateNote);
        return history;
    }


}
