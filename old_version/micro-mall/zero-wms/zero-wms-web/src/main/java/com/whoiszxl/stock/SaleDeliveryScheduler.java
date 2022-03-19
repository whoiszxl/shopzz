package com.whoiszxl.stock;

import com.whoiszxl.dto.OrderItemDTO;
import com.whoiszxl.entity.schedule.SaleDeliveryScheduleResult;

/**
 * 销售出库调度器
 *
 * @author whoiszxl
 * @date 2021/8/11
 */
public interface SaleDeliveryScheduler {


    /**
     * 调度销售出库
     * @param orderItem 订单条目
     * @return 调度结果
     * @throws Exception
     */
    SaleDeliveryScheduleResult schedule(OrderItemDTO orderItem);



    /**
     * 获取订单条目的调度结果
     * @param orderItem 订单条目
     * @return 调度结果
     * @throws Exception
     */
    SaleDeliveryScheduleResult getScheduleResult(OrderItemDTO orderItem);
}
