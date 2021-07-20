package com.whoiszxl.client;

import com.whoiszxl.constant.PurchaseInboundOrderStatus;
import com.whoiszxl.dto.PurchaseInboundOrderDTO;
import com.whoiszxl.dto.PurchaseInboundOrderItemDTO;
import com.whoiszxl.dto.PurchaseOrderDTO;
import com.whoiszxl.dto.PurchaseOrderItemDTO;
import com.whoiszxl.feign.WmsFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 调度中心Feign暴露的对外接口
 *
 * @author whoiszxl
 * @date 2021/7/20
 */
@Slf4j
@RestController
public class DispatchClientImpl implements DispatchClient {

    @Autowired
    private WmsFeignClient wmsFeignClient;


    @Override
    public Boolean dispatchPurchaseInBound(@RequestBody PurchaseOrderDTO purchaseOrderDTO) {
        //1. 创建采购入库订单
        PurchaseInboundOrderDTO purchaseInboundOrderDTO = createPurchaseInboundOrder(purchaseOrderDTO);

        //2. 创建采购入库订单条目
        List<PurchaseInboundOrderItemDTO> purchaseInboundOrderItemDTOList = new ArrayList<>();
        for (PurchaseOrderItemDTO item : purchaseOrderDTO.getItems()) {
            purchaseInboundOrderItemDTOList.add(createPurchaseInboundOrderItem(item));
        }
        purchaseInboundOrderDTO.setItems(purchaseInboundOrderItemDTOList);

        //3. 调用wms模块，将入库单入库
        wmsFeignClient.createPurchaseInboundOrder(purchaseInboundOrderDTO);
        return true;
    }

    /**
     * 将采购单条目对象转换为入库单条目对象
     * @param item 采购单条目
     * @return 入库单条目
     */
    private PurchaseInboundOrderItemDTO createPurchaseInboundOrderItem(PurchaseOrderItemDTO item) {
        PurchaseInboundOrderItemDTO purchaseInboundOrderItemDTO = item.clone(PurchaseInboundOrderItemDTO.class);
        purchaseInboundOrderItemDTO.setId(null);
        return purchaseInboundOrderItemDTO;
    }

    /**
     * 将采购单对象转换为入库单对象
     * @param purchaseOrderDTO 采购单DTO
     * @return 入库单DTO
     */
    private PurchaseInboundOrderDTO createPurchaseInboundOrder(PurchaseOrderDTO purchaseOrderDTO) {
        PurchaseInboundOrderDTO purchaseInboundOrderDTO = purchaseOrderDTO.clone(PurchaseInboundOrderDTO.class);
        purchaseInboundOrderDTO.setId(null);
        purchaseInboundOrderDTO.setPurchaseOrderId(purchaseOrderDTO.getId());
        purchaseInboundOrderDTO.setPurchaseInboundOrderStatus(PurchaseInboundOrderStatus.EDITING);
        purchaseInboundOrderDTO.setPurchaseContactor(purchaseOrderDTO.getContactor());
        purchaseInboundOrderDTO.setPurchaseContactPhoneNumber(purchaseOrderDTO.getContactPhoneNumber());
        purchaseInboundOrderDTO.setPurchaseContactEmail(purchaseOrderDTO.getContactEmail());
        purchaseInboundOrderDTO.setPurchaseInboundOrderComment(purchaseOrderDTO.getComment());
        return purchaseInboundOrderDTO;
    }
}
