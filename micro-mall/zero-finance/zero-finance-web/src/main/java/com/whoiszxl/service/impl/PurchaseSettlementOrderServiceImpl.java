package com.whoiszxl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.whoiszxl.constants.PurchaseSettlementOrderApproveResult;
import com.whoiszxl.constants.PurchaseSettlementOrderStatus;
import com.whoiszxl.dto.PurchaseSettlementOrderDTO;
import com.whoiszxl.dto.PurchaseSettlementOrderItemDTO;
import com.whoiszxl.entity.PurchaseSettlementOrder;
import com.whoiszxl.entity.PurchaseSettlementOrderItem;
import com.whoiszxl.entity.vo.PurchaseSettlementOrderVO;
import com.whoiszxl.exception.ExceptionCatcher;
import com.whoiszxl.mapper.PurchaseSettlementOrderMapper;
import com.whoiszxl.service.PurchaseSettlementOrderItemService;
import com.whoiszxl.service.PurchaseSettlementOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.utils.BeanCopierUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        if(PurchaseSettlementOrderApproveResult.REJECTED.equals(status)) {
            this.updateStatus(id, PurchaseSettlementOrderStatus.EDITING);
        }else if(PurchaseSettlementOrderApproveResult.PASSED.equals(status)) {
            this.updateStatus(id, PurchaseSettlementOrderStatus.FINISHED);
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
}
