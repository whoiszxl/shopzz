package com.whoiszxl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.constants.PurchaseOrderStatusConstants;
import com.whoiszxl.cqrs.command.PurchaseOrderSaveCommand;
import com.whoiszxl.cqrs.response.PurchaseOrderResponse;
import com.whoiszxl.cqrs.vo.PurchaseOrderItemVO;
import com.whoiszxl.dozer.DozerUtils;
import com.whoiszxl.entity.PurchaseOrder;
import com.whoiszxl.entity.PurchaseOrderItem;
import com.whoiszxl.enums.PurchaseOrderStatusEnum;
import com.whoiszxl.mapper.PurchaseOrderMapper;
import com.whoiszxl.service.PurchaseOrderItemService;
import com.whoiszxl.service.PurchaseOrderService;
import com.whoiszxl.utils.BeanCopierUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 采购单服务实现
 *
 * @author whoiszxl
 * @date 2022/3/18
 */
@Slf4j
@Service
public class PurchaseOrderServiceImpl extends ServiceImpl<PurchaseOrderMapper, PurchaseOrder> implements PurchaseOrderService {

    @Autowired
    private PurchaseOrderMapper purchaseOrderMapper;

    @Autowired
    private PurchaseOrderItemService purchaseOrderItemService;

    @Autowired
    private DozerUtils dozerUtils;

    @Override
    public void savePurchaseOrder(PurchaseOrderSaveCommand purchaseOrderSaveCommand) {
        PurchaseOrder purchaseOrder = dozerUtils.map(purchaseOrderSaveCommand, PurchaseOrder.class);

        //1. 新增采购订单
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatusEnum.EDITING.getCode());
        this.save(purchaseOrder);

        //2. 批量新增采购订单中的商品详情
        List<PurchaseOrderItemVO> items = purchaseOrderSaveCommand.getItems();
        for (PurchaseOrderItemVO item : items) {
            item.setPurchaseOrderId(purchaseOrder.getId());
        }
        List<PurchaseOrderItem> purchaseOrderItems = dozerUtils.mapList(items, PurchaseOrderItem.class);
        purchaseOrderItemService.saveBatch(purchaseOrderItems);
    }

    @Override
    public PurchaseOrderResponse getPurchaseOrderById(Long id) {
        //1. 查询采购订单
        PurchaseOrder purchaseOrder = this.getById(id);
        PurchaseOrderResponse purchaseOrderResponse = dozerUtils.map(purchaseOrder, PurchaseOrderResponse.class);

        //2. 查询采购订单中的商品详情,将详情添加到采购订单对象中去
        List<PurchaseOrderItem> purchaseOrderItemList = purchaseOrderItemService.list(
                new LambdaQueryWrapper<PurchaseOrderItem>().eq(PurchaseOrderItem::getPurchaseOrderId, id));

        List<PurchaseOrderItemVO> purchaseOrderItemVOList = dozerUtils.mapList(purchaseOrderItemList, PurchaseOrderItemVO.class);
        purchaseOrderResponse.setItems(purchaseOrderItemVOList);
        return purchaseOrderResponse;
    }

    @Override
    @Transactional
    public void updatePurchaseOrder(PurchaseOrderSaveCommand purchaseOrderSaveCommand) {
        //1. 更新采购订单
        PurchaseOrder purchaseOrder = dozerUtils.map(purchaseOrderSaveCommand, PurchaseOrder.class);
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatusEnum.EDITING.getCode());
        this.updateById(purchaseOrder);

        //2. 删除原来的订单商品详情
        purchaseOrderItemService.remove(new UpdateWrapper<PurchaseOrderItem>()
                .eq("purchase_order_id", purchaseOrder.getId()));

        //2. 新增订单商品详情
        List<PurchaseOrderItemVO> items = purchaseOrderSaveCommand.getItems();
        items.forEach(item -> item.setPurchaseOrderId(purchaseOrder.getId()));
        List<PurchaseOrderItem> purchaseOrderItems = BeanCopierUtils.copyListProperties(items, PurchaseOrderItem::new);

        purchaseOrderItemService.saveBatch(purchaseOrderItems);
    }


    @Override
    public boolean updateStatus(Long id, Integer status) {
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setId(id);
        purchaseOrder.setPurchaseOrderStatus(status);
        return this.updateById(purchaseOrder);
    }

    @Override
    public boolean updateFinishedBySupplierId(Long id) {
        UpdateWrapper<PurchaseOrder> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("supplier_id", id);

        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setSupplierId(id);
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatusConstants.FINISHED);
        return this.update(purchaseOrder, updateWrapper);
    }
}
