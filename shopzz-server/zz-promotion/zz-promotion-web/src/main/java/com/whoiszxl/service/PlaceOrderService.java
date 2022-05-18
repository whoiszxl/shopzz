package com.whoiszxl.service;

import com.whoiszxl.cqrs.command.SeckillOrderSubmitCommand;

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
}
