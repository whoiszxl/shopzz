package com.whoiszxl.client;

import com.whoiszxl.dto.PurchaseInboundOrderDTO;
import com.whoiszxl.dto.PurchaseInboundOrderItemDTO;
import com.whoiszxl.dto.PurchaseSettlementOrderDTO;
import com.whoiszxl.dto.PurchaseSettlementOrderItemDTO;
import com.whoiszxl.entity.PurchaseSettlementOrderItem;
import com.whoiszxl.feign.FinanceFeignClient;
import com.whoiszxl.feign.WmsFeignClient;
import com.whoiszxl.service.PurchaseSettlementOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 财务中心feign对外请求接口
 *
 * @author whoiszxl
 * @date 2021/7/21
 */
@RestController
public class FinanceFeignClientImpl implements FinanceFeignClient {

    @Autowired
    private PurchaseSettlementOrderService purchaseSettlementOrderService;

    @Autowired
    private WmsFeignClient wmsFeignClient;

    @Override
    @PostMapping("/createPurchaseSettlementOrder")
    public Boolean createPurchaseSettlementOrder(@RequestBody PurchaseInboundOrderDTO purchaseInboundOrderDTO) {
        //1. 将采购入库单的数据填充到采购结算单中
        PurchaseSettlementOrderDTO settlementOrderDTO = purchaseInboundOrderDTO.clone(PurchaseSettlementOrderDTO.class);
        settlementOrderDTO.setId(null);
        settlementOrderDTO.setStatus(null);

        //2. 将采购入库条目信息填充到采购结算条目中
        List<PurchaseSettlementOrderItemDTO> items = new ArrayList<>();
        for (PurchaseInboundOrderItemDTO purchaseInboundOrderItemDTO : purchaseInboundOrderDTO.getItems()) {
            PurchaseSettlementOrderItemDTO item = purchaseInboundOrderItemDTO.clone(PurchaseSettlementOrderItemDTO.class);
            item.setId(null);
            items.add(item);
        }
        settlementOrderDTO.setItems(items);

        //3. 将采购结算单与条目新增到数据库
        purchaseSettlementOrderService.savePurchaseSettlementOrderAndItem(settlementOrderDTO);

        //4. 通知WMS中心，采购结算单创建了
        wmsFeignClient.notifyCreatePurchaseSettlementOrderEvent(settlementOrderDTO.getPurchaseInboundOrderId());

        return true;
    }
}
