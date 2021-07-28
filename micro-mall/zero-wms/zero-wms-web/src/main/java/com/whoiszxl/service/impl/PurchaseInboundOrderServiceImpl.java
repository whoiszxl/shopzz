package com.whoiszxl.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.constants.PurchaseInboundOrderStatusConstants;
import com.whoiszxl.constants.PurchaseOrderStatusConstants;
import com.whoiszxl.dto.PurchaseInboundOnItemDTO;
import com.whoiszxl.dto.PurchaseInboundOrderDTO;
import com.whoiszxl.dto.PurchaseInboundOrderItemDTO;
import com.whoiszxl.entity.PurchaseInboundOnItem;
import com.whoiszxl.entity.PurchaseInboundOrder;
import com.whoiszxl.entity.PurchaseInboundOrderItem;
import com.whoiszxl.entity.vo.PurchaseInboundOrderVO;
import com.whoiszxl.mapper.PurchaseInboundOrderMapper;
import com.whoiszxl.service.PurchaseInboundOnItemService;
import com.whoiszxl.service.PurchaseInboundOrderItemService;
import com.whoiszxl.service.PurchaseInboundOrderService;
import com.whoiszxl.service.PurchaseOrderService;
import com.whoiszxl.utils.BeanCopierUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 采购入库订单表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-20
 */
@Service
public class PurchaseInboundOrderServiceImpl extends ServiceImpl<PurchaseInboundOrderMapper, PurchaseInboundOrder> implements PurchaseInboundOrderService {

    @Autowired
    private PurchaseInboundOrderItemService purchaseInboundOrderItemService;

    @Autowired
    private PurchaseInboundOnItemService purchaseInboundOnItemService;

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @Override
    public PurchaseInboundOrderDTO getPurchaseInboundOrderById(Long id) {
        //1. 查询采购入库单
        PurchaseInboundOrder purchaseInboundOrder = this.getById(id);
        PurchaseInboundOrderDTO purchaseInboundOrderDTO = purchaseInboundOrder.clone(PurchaseInboundOrderDTO.class);

        //2. 查询采购入库单条目集合
        List<PurchaseInboundOrderItem> purchaseInboundOrderItems = purchaseInboundOrderItemService.listByPurchaseInboundOrderId(id);
        List<PurchaseInboundOrderItemDTO> purchaseInboundOrderItemDTOList = BeanCopierUtils.copyListProperties(purchaseInboundOrderItems, PurchaseInboundOrderItemDTO::new);

        //3. 获取上架条目信息
        for (PurchaseInboundOrderItemDTO purchaseInboundOrderItemDTO : purchaseInboundOrderItemDTOList) {
            List<PurchaseInboundOnItem> onItems = purchaseInboundOnItemService.listByPurchaseInboundOrderItemId(purchaseInboundOrderItemDTO.getId());
            List<PurchaseInboundOnItemDTO> onItemDTOs = BeanCopierUtils.copyListProperties(onItems, PurchaseInboundOnItemDTO::new);
            purchaseInboundOrderItemDTO.setOnItemDTOs(onItemDTOs);
        }

        //4. 将入库单商品条目设置到入库单中
        purchaseInboundOrderDTO.setItems(purchaseInboundOrderItemDTOList);

        return purchaseInboundOrderDTO;
    }

    @Override
    public Boolean updatePurchaseInboundOrder(PurchaseInboundOrderVO purchaseInboundOrderVO) {
        //1. 更新采购入库单的到达时间，更新采购入库单的状态
        PurchaseInboundOrder purchaseInboundOrder = new PurchaseInboundOrder();
        purchaseInboundOrder.setId(purchaseInboundOrderVO.getId());
        purchaseInboundOrder.setPurchaseInboundOrderStatus(PurchaseInboundOrderStatusConstants.EDITING);
        purchaseInboundOrder.setArrivalTime(purchaseInboundOrderVO.getArrivalTime());
        this.updateById(purchaseInboundOrder);

        //2. 更新采购入库单条目的状态
        PurchaseInboundOrderItem updateItem = new PurchaseInboundOrderItem();
        for (PurchaseInboundOrderItemDTO item : purchaseInboundOrderVO.getItems()) {
            updateItem.setId(item.getId());
            updateItem.setQualifiedCount(item.getQualifiedCount());
            updateItem.setArrivalCount(item.getArrivalCount());
            purchaseInboundOrderItemService.updateById(updateItem);
        }

        return true;
    }


    @Override
    public void updateStatus(Long id, Integer status) {
        PurchaseInboundOrder purchaseInboundOrder = new PurchaseInboundOrder();
        purchaseInboundOrder.setId(id);
        purchaseInboundOrder.setPurchaseInboundOrderStatus(status);
        this.updateById(purchaseInboundOrder);
    }



    /**
     * 创建采购入库订单
     * @param purchaseInboundOrderDTO 采购入库订单DTO
     * @return 处理结果
     */
    @Transactional
    @Override
    public ResponseResult<Boolean> savePurchaseInboundOrder(PurchaseInboundOrderDTO purchaseInboundOrderDTO) {
        //1. 将入库单保存到数据库
        PurchaseInboundOrder purchaseInboundOrder = purchaseInboundOrderDTO.clone(PurchaseInboundOrder.class);
        this.save(purchaseInboundOrder);

        //2. 将入库单商品条目批量保存到数据库
        List<PurchaseInboundOrderItem> purchaseInboundOrderItems = BeanCopierUtils.copyListProperties(purchaseInboundOrderDTO.getItems(), PurchaseInboundOrderItem::new);
        purchaseInboundOrderItems.forEach(item -> {
            item.setPurchaseInboundOrderId(purchaseInboundOrder.getId());
        });

        purchaseInboundOrderItemService.saveBatch(purchaseInboundOrderItems);

        //3. 更新采购单的状态为待入库
        purchaseOrderService.updateStatus(purchaseInboundOrderDTO.getPurchaseOrderId(), PurchaseOrderStatusConstants.WAIT_FOR_INBOUND);
        return ResponseResult.buildSuccess();
    }
}
