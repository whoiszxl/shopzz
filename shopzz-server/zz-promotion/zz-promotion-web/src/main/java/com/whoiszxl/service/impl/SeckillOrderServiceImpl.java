package com.whoiszxl.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.entity.SeckillOrder;
import com.whoiszxl.enums.promotion.SeckillOrderStatusEnum;
import com.whoiszxl.mapper.SeckillOrderMapper;
import com.whoiszxl.service.SeckillOrderService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 秒杀订单表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2022-04-19
 */
@Service
public class SeckillOrderServiceImpl extends ServiceImpl<SeckillOrderMapper, SeckillOrder> implements SeckillOrderService {

    @Override
    public boolean orderCancel(Long orderId) {
        return this.update(Wrappers.<SeckillOrder>lambdaUpdate()
                .set(SeckillOrder::getStatus, SeckillOrderStatusEnum.CANCEL.getCode())
                .eq(SeckillOrder::getId, orderId));
    }
}
