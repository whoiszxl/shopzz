package com.whoiszxl.taowu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whoiszxl.taowu.entity.SeckillOrder;

/**
 * <p>
 * 秒杀订单表 服务类
 * </p>
 *
 * @author whoiszxl
 * @since 2022-04-19
 */
public interface SeckillOrderService extends IService<SeckillOrder> {

    /**
     * 秒杀订单取消
     * @param orderId
     * @return
     */
    boolean orderCancel(Long orderId);
}
