package com.whoiszxl.service;

import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.dto.PurchaseInboundOrderDTO;
import com.whoiszxl.entity.PurchaseInboundOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import com.whoiszxl.entity.vo.PurchaseInboundOrderVO;

/**
 * <p>
 * 采购入库订单表 服务类
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-20
 */
public interface PurchaseInboundOrderService extends IService<PurchaseInboundOrder> {

    /**
     * 通过主键ID查询采购入库单
     * @param id 主键ID
     * @return 采购入库单DTO
     */
    PurchaseInboundOrderDTO getPurchaseInboundOrderById(Long id);

    /**
     * 更新采购入库单
     * @param purchaseInboundOrderVO 采购入库单
     * @return
     */
    Boolean updatePurchaseInboundOrder(PurchaseInboundOrderVO purchaseInboundOrderVO);

    /**
     * 更新采购入库单状态
     * @param id 采购入库单ID
     * @param status 状态
     */
    void updateStatus(Long id, Integer status);

    /**
     * 创建采购入库订单
     * @param purchaseInboundOrderDTO 采购入库订单DTO
     * @return 处理结果
     */
    ResponseResult<Boolean> savePurchaseInboundOrder(PurchaseInboundOrderDTO purchaseInboundOrderDTO);
}
