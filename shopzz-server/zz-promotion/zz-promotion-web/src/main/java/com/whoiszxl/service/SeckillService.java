package com.whoiszxl.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whoiszxl.cqrs.command.SeckillOrderSubmitCommand;
import com.whoiszxl.entity.Seckill;

/**
 * <p>
 * 秒杀表 服务类
 * </p>
 *
 * @author whoiszxl
 * @since 2022-04-19
 */
public interface SeckillService extends IService<Seckill> {

    /**
     * 秒杀订单下单
     * @param seckillOrderSubmitCommand 秒杀下单命令
     * @return 订单ID
     */
    Long orderSubmit(SeckillOrderSubmitCommand seckillOrderSubmitCommand);
}
