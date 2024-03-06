package com.whoiszxl.taowu.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.taowu.entity.SeckillOrder;
import com.whoiszxl.taowu.enums.SeckillOrderStatusEnum;
import com.whoiszxl.taowu.mapper.SeckillOrderMapper;
import com.whoiszxl.taowu.service.SeckillOrderService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class SeckillOrderServiceImpl extends ServiceImpl<SeckillOrderMapper, SeckillOrder> implements SeckillOrderService {

    @Override
    public boolean orderCancel(Long orderId) {
        return this.update(Wrappers.<SeckillOrder>lambdaUpdate()
                .set(SeckillOrder::getStatus, SeckillOrderStatusEnum.CANCEL.getCode())
                .eq(SeckillOrder::getId, orderId));
    }
}
