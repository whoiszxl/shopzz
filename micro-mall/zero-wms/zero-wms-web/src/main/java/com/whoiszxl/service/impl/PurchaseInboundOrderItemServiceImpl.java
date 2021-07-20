package com.whoiszxl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.whoiszxl.entity.PurchaseInboundOrderItem;
import com.whoiszxl.mapper.PurchaseInboundOrderItemMapper;
import com.whoiszxl.service.PurchaseInboundOrderItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 采购入库订单条目表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-20
 */
@Service
public class PurchaseInboundOrderItemServiceImpl extends ServiceImpl<PurchaseInboundOrderItemMapper, PurchaseInboundOrderItem> implements PurchaseInboundOrderItemService {

    @Override
    public List<PurchaseInboundOrderItem> listByPurchaseInboundOrderId(Long id) {
        QueryWrapper wrapper = new QueryWrapper<PurchaseInboundOrderItem>();
        wrapper.eq("purchase_inbound_order_id", id);
        return this.list(wrapper);
    }
}
