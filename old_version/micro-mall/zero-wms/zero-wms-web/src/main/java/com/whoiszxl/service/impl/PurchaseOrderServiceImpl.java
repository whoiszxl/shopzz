package com.whoiszxl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.whoiszxl.constants.PurchaseOrderStatusConstants;
import com.whoiszxl.dto.PurchaseInboundOnItemDTO;
import com.whoiszxl.dto.PurchaseOrderDTO;
import com.whoiszxl.dto.PurchaseOrderItemDTO;
import com.whoiszxl.entity.PurchaseInboundOnItem;
import com.whoiszxl.entity.PurchaseOrder;
import com.whoiszxl.entity.PurchaseOrderItem;
import com.whoiszxl.entity.vo.PurchaseOrderItemVO;
import com.whoiszxl.entity.vo.PurchaseOrderVO;
import com.whoiszxl.enums.PurchaseOrderStatusEnum;
import com.whoiszxl.exception.ExceptionCatcher;
import com.whoiszxl.mapper.PurchaseOrderMapper;
import com.whoiszxl.service.PurchaseInboundOnItemService;
import com.whoiszxl.service.PurchaseOrderItemService;
import com.whoiszxl.service.PurchaseOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.utils.BeanCopierUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 采购订单表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-19
 */
@Service
public class PurchaseOrderServiceImpl extends ServiceImpl<PurchaseOrderMapper, PurchaseOrder> implements PurchaseOrderService {

    @Autowired
    private PurchaseOrderItemService purchaseOrderItemService;

    @Autowired
    private PurchaseInboundOnItemService purchaseInboundOnItemService;



    @Transactional
    @Override
    public boolean savePurchaseOrder(PurchaseOrderVO purchaseOrderVO) {
        //1. 新增采购订单
        purchaseOrderVO.setPurchaseOrderStatus(PurchaseOrderStatusEnum.EDITING.getCode());
        PurchaseOrder purchaseOrder = purchaseOrderVO.clone(PurchaseOrder.class);
        boolean orderFlag = this.save(purchaseOrder);

        //2. 新增采购订单中的商品详情
        List<PurchaseOrderItemVO> items = purchaseOrderVO.getItems();
        for (PurchaseOrderItemVO item : items) {
            item.setPurchaseOrderId(purchaseOrder.getId());
        }

        List<PurchaseOrderItem> purchaseOrderItems = BeanCopierUtils.copyListProperties(items, PurchaseOrderItem::new);
        boolean itemFlag = purchaseOrderItemService.saveBatch(purchaseOrderItems);

        return orderFlag && itemFlag;
    }

    @Override
    public PurchaseOrderDTO getPurchaseOrderById(Long id) {
        //1. 查询采购订单
        PurchaseOrder purchaseOrder = this.getById(id);
        PurchaseOrderDTO purchaseOrderDTO = purchaseOrder.clone(PurchaseOrderDTO.class);

        //2. 查询采购订单中的商品详情,将详情添加到采购订单对象中去
        List<PurchaseOrderItem> purchaseOrderItemList = purchaseOrderItemService.list(new QueryWrapper<PurchaseOrderItem>().eq("purchase_order_id", id));
        List<PurchaseOrderItemDTO> purchaseOrderItemDTOList = BeanCopierUtils.copyListProperties(purchaseOrderItemList, PurchaseOrderItemDTO::new);
        purchaseOrderDTO.setItems(purchaseOrderItemDTOList);

        //4. 获取上架条目信息
        for (PurchaseOrderItemDTO purchaseOrderItemDTO : purchaseOrderItemDTOList) {
            List<PurchaseInboundOnItem> onItems = purchaseInboundOnItemService.listByPurchaseOrderItemId(purchaseOrderItemDTO.getId());
            List<PurchaseInboundOnItemDTO> onItemDTOs = BeanCopierUtils.copyListProperties(onItems, PurchaseInboundOnItemDTO::new);
            purchaseOrderItemDTO.setOnItems(onItemDTOs);
        }

        return purchaseOrderDTO;
    }

    @Transactional
    @Override
    public Boolean updatePurchaseOrder(PurchaseOrderVO purchaseOrderVO) {
        //1. 更新采购订单
        purchaseOrderVO.setPurchaseOrderStatus(PurchaseOrderStatusEnum.EDITING.getCode());
        PurchaseOrder purchaseOrder = purchaseOrderVO.clone(PurchaseOrder.class);
        boolean orderFlag = this.updateById(purchaseOrder);

        //2. 删除原来的订单商品详情
        boolean removeFlag = purchaseOrderItemService.remove(new UpdateWrapper<PurchaseOrderItem>()
                .eq("purchase_order_id", purchaseOrderVO.getId()));

        //2. 新增订单商品详情
        List<PurchaseOrderItemVO> items = purchaseOrderVO.getItems();
        items.forEach(item -> item.setPurchaseOrderId(purchaseOrder.getId()));
        List<PurchaseOrderItem> purchaseOrderItems = BeanCopierUtils.copyListProperties(items, PurchaseOrderItem::new);
        boolean itemFlag = purchaseOrderItemService.saveBatch(purchaseOrderItems);

        if(!orderFlag || !removeFlag || !itemFlag) {
            ExceptionCatcher.catchDatabaseFailEx();
        }
        return true;
    }

    @Transactional
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
