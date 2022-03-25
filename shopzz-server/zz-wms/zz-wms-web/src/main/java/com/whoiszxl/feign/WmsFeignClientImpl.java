package com.whoiszxl.feign;

import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.constants.WmsStockUpdateEventConstants;
import com.whoiszxl.dto.OrderInfoDTO;
import com.whoiszxl.dto.OrderItemDTO;
import com.whoiszxl.service.PurchaseOrderService;
import com.whoiszxl.stock.WmsStockUpdater;
import com.whoiszxl.stock.WmsStockUpdaterFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO
 *
 * @author whoiszxl
 * @date 2022/3/18
 */
@Slf4j
@RestController
public class WmsFeignClientImpl implements WmsFeignClient {

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @Autowired
    private WmsStockUpdaterFactory wmsStockUpdaterFactory;


    @Override
    public ResponseResult<Boolean> notifyPayOrderSuccess(OrderInfoDTO orderInfo) {
        //更新WMS中心库存
        for (OrderItemDTO orderItemDTO : orderInfo.getOrderItemDTOList()) {
            WmsStockUpdater stockUpdater = wmsStockUpdaterFactory.create(WmsStockUpdateEventConstants.PAY_ORDER, null);
            stockUpdater.update();
        }

        //TODO 新增出库单，需要创建发货单，物流单，构建好出库单信息
        return ResponseResult.buildSuccess();
    }
}
