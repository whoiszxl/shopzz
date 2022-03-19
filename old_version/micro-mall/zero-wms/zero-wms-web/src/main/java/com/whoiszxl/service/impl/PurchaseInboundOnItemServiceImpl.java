package com.whoiszxl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.whoiszxl.entity.PurchaseInboundOnItem;
import com.whoiszxl.mapper.PurchaseInboundOnItemMapper;
import com.whoiszxl.service.PurchaseInboundOnItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 采购入库订单条目关联的上架条目表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-20
 */
@Service
public class PurchaseInboundOnItemServiceImpl extends ServiceImpl<PurchaseInboundOnItemMapper, PurchaseInboundOnItem> implements PurchaseInboundOnItemService {

    @Override
    public List<PurchaseInboundOnItem> listByPurchaseOrderItemId(Long id) {
        QueryWrapper wrapper = new QueryWrapper<PurchaseInboundOnItem>();
        wrapper.eq("purchase_order_item_id", id);
        return this.list(wrapper);
    }
}
