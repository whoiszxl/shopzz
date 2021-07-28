package com.whoiszxl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.constants.PurchaseInboundOrderStatusConstants;
import com.whoiszxl.constants.PurchaseOrderStatusConstants;
import com.whoiszxl.constants.PurchaseSettlementOrderApproveResultConstants;
import com.whoiszxl.constants.PurchaseSettlementOrderStatusConstants;
import com.whoiszxl.dto.PurchaseInboundOrderDTO;
import com.whoiszxl.dto.PurchaseInboundOrderItemDTO;
import com.whoiszxl.dto.PurchaseSettlementOrderDTO;
import com.whoiszxl.dto.PurchaseSettlementOrderItemDTO;
import com.whoiszxl.entity.PurchaseSettlementOrder;
import com.whoiszxl.entity.PurchaseSettlementOrderItem;
import com.whoiszxl.entity.vo.PurchaseSettlementOrderVO;
import com.whoiszxl.exception.ExceptionCatcher;
import com.whoiszxl.feign.WmsFeignClient;
import com.whoiszxl.mapper.PurchaseSettlementOrderMapper;
import com.whoiszxl.service.PurchaseInboundOrderService;
import com.whoiszxl.service.PurchaseOrderService;
import com.whoiszxl.service.PurchaseSettlementOrderItemService;
import com.whoiszxl.service.PurchaseSettlementOrderService;
import com.whoiszxl.utils.BeanCopierUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 财务中心的采购结算单表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-21
 */
@Service
public class PurchaseSettlementOrderServiceImpl extends ServiceImpl<PurchaseSettlementOrderMapper, PurchaseSettlementOrder> implements PurchaseSettlementOrderService {

    @Autowired
    private PurchaseSettlementOrderItemService purchaseSettlementOrderItemService;

    @Autowired
    private PurchaseInboundOrderService purchaseInboundOrderService;

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @Autowired
    private WmsFeignClient wmsFeignClient;

    @Override
    public PurchaseSettlementOrderDTO getPurchaseSettlementOrderById(Long id) {
        //1. 查询采购结算单
        PurchaseSettlementOrder purchaseSettlementOrder = this.getById(id);
        PurchaseSettlementOrderDTO purchaseSettlementOrderDTO = purchaseSettlementOrder.clone(PurchaseSettlementOrderDTO.class);

        //2. 查询采购结算单条目
        QueryWrapper queryWrapper = new QueryWrapper<PurchaseSettlementOrderItem>();
        queryWrapper.eq("purchase_settlement_order_id", id);
        List<PurchaseSettlementOrderItem> itemList = purchaseSettlementOrderItemService.list(queryWrapper);
        List<PurchaseSettlementOrderItemDTO> purchaseSettlementOrderItemDTOS = BeanCopierUtils.copyListProperties(itemList, PurchaseSettlementOrderItemDTO::new);
        purchaseSettlementOrderDTO.setItems(purchaseSettlementOrderItemDTOS);

        return purchaseSettlementOrderDTO;
    }

    @Override
    public Boolean updateSettlementOrder(PurchaseSettlementOrderVO purchaseSettlementOrderVO) {
        return this.updateById(purchaseSettlementOrderVO.clone(PurchaseSettlementOrder.class));
    }

    @Override
    public void approve(Long id, Integer status) {
        if(PurchaseSettlementOrderApproveResultConstants.REJECTED.equals(status)) {
            this.updateStatus(id, PurchaseSettlementOrderStatusConstants.EDITING);
        }else if(PurchaseSettlementOrderApproveResultConstants.PASSED.equals(status)) {
            this.updateStatus(id, PurchaseSettlementOrderStatusConstants.APPROVED);
        }
    }

    @Override
    public void updateStatus(Long id, Integer status) {
        PurchaseSettlementOrder result = this.getById(id);
        if(result == null) {
            ExceptionCatcher.catchDatabaseFailEx();
        }

        result.setStatus(status);
        this.updateById(result);
    }

    @Override
    public void savePurchaseSettlementOrderAndItem(PurchaseSettlementOrderDTO settlementOrderDTO) {
        //1. 新增采购结算单
        settlementOrderDTO.setStatus(PurchaseSettlementOrderStatusConstants.EDITING);

        BigDecimal totalSettlementAmount = BigDecimal.ZERO;
        for (PurchaseSettlementOrderItemDTO item : settlementOrderDTO.getItems()) {
            totalSettlementAmount = totalSettlementAmount.add(item.getPurchasePrice().multiply(new BigDecimal(item.getArrivalCount())));
        }
        settlementOrderDTO.setTotalSettlementAmount(totalSettlementAmount);

        PurchaseSettlementOrder purchaseSettlementOrder = settlementOrderDTO.clone(PurchaseSettlementOrder.class);
        this.save(purchaseSettlementOrder);

        //2. 新增采购结算条目单
        List<PurchaseSettlementOrderItem> settlementOrderItemList
                = BeanCopierUtils.copyListProperties(settlementOrderDTO.getItems(), PurchaseSettlementOrderItem::new);

        settlementOrderItemList.forEach(item -> item.setPurchaseSettlementOrderId(purchaseSettlementOrder.getId()));
        purchaseSettlementOrderItemService.saveBatch(settlementOrderItemList);
    }



    @Override
    public boolean createPurchaseSettlementOrder(PurchaseInboundOrderDTO purchaseInboundOrderDTO) {
        //1. 将采购入库单的数据填充到采购结算单中
        PurchaseSettlementOrderDTO settlementOrderDTO = purchaseInboundOrderDTO.clone(PurchaseSettlementOrderDTO.class);
        settlementOrderDTO.setId(null);
        settlementOrderDTO.setStatus(null);
        settlementOrderDTO.setTotalSettlementAmount(BigDecimal.ZERO);
        settlementOrderDTO.setPurchaseInboundOrderId(purchaseInboundOrderDTO.getId());
        settlementOrderDTO.setComment("");

        //2. 将采购入库条目信息填充到采购结算条目中
        List<PurchaseSettlementOrderItemDTO> items = new ArrayList<>();
        for (PurchaseInboundOrderItemDTO purchaseInboundOrderItemDTO : purchaseInboundOrderDTO.getItems()) {
            PurchaseSettlementOrderItemDTO item = purchaseInboundOrderItemDTO.clone(PurchaseSettlementOrderItemDTO.class);
            item.setId(null);
            items.add(item);
        }
        settlementOrderDTO.setItems(items);

        //3. 将采购结算单与条目新增到数据库
        this.savePurchaseSettlementOrderAndItem(settlementOrderDTO);

        //4. 通知WMS中心，采购结算单创建了

        //4.1. 更新采购入库单的状态为待结算
        PurchaseInboundOrderDTO updateParams = purchaseInboundOrderService.getPurchaseInboundOrderById(settlementOrderDTO.getPurchaseInboundOrderId());
        purchaseInboundOrderService.updateStatus(settlementOrderDTO.getPurchaseInboundOrderId(), PurchaseInboundOrderStatusConstants.WAIT_FOR_SETTLEMENT);

        //4.2. 更新采购单的状态为待结算
        purchaseOrderService.updateStatus(updateParams.getPurchaseOrderId(), PurchaseOrderStatusConstants.WAIT_FOR_SETTLEMENT);

        return true;
    }
}
