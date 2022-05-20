package com.whoiszxl.service;

import com.whoiszxl.cqrs.command.SeckillOrderResultCommand;
import com.whoiszxl.cqrs.command.SeckillOrderSubmitCommand;
import com.whoiszxl.cqrs.dto.SeckillPlaceOrderDTO;

/**
 * 下单操作服务接口
 *
 * @author whoiszxl
 * @date 2022/5/18
 */
public interface PlaceOrderService {

    /**
     * 秒杀下单
     * @param memberId
     * @param seckillOrderSubmitCommand
     * @return
     */
    Long doPlaceOrder(Long memberId, SeckillOrderSubmitCommand seckillOrderSubmitCommand);

    /**
     * 秒杀下单队列任务消费处理方法
     * @param seckillPlaceOrderDTO
     */
    void handlePlaceOrderTask(SeckillPlaceOrderDTO seckillPlaceOrderDTO);

    /**
     * 获取秒杀订单结果
     * @param seckillOrderResultCommand
     * @return
     */
    Long getOrderResult(SeckillOrderResultCommand seckillOrderResultCommand);
}
