package com.whoiszxl.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.constants.PurchaseInboundOrderApproveResultConstants;
import com.whoiszxl.constants.PurchaseOrderStatusConstants;
import com.whoiszxl.cqrs.command.PurchaseOrderSaveCommand;
import com.whoiszxl.cqrs.command.ReceiveProductCommand;
import com.whoiszxl.cqrs.query.PurchaseOrderQuery;
import com.whoiszxl.cqrs.response.PurchaseOrderResponse;
import com.whoiszxl.dozer.DozerUtils;
import com.whoiszxl.entity.PurchaseOrder;
import com.whoiszxl.entity.PurchaseOrderItem;
import com.whoiszxl.enums.PurchaseOrderApproveEnum;
import com.whoiszxl.enums.PurchaseOrderStatusEnum;
import com.whoiszxl.factory.PurchaseOrderHandlerFactory;
import com.whoiszxl.factory.handler.PurchaseOrderHandler;
import com.whoiszxl.service.PurchaseOrderItemService;
import com.whoiszxl.service.PurchaseOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 采购相关接口
 *
 * @author whoiszxl
 * @date 2022/3/18
 */
@RestController
@RequestMapping("/purchase/order")
@Api(tags = "采购相关接口")
public class PurchaseOrderController {

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @Autowired
    private PurchaseOrderItemService purchaseOrderItemService;

    @Autowired
    private DozerUtils dozerUtils;

    @Autowired
    private PurchaseOrderHandlerFactory purchaseOrderHandlerFactory;


    @SaCheckLogin
    //@SaCheckPermission("wms:purchase:list")
    @PostMapping("/list")
    @ApiOperation(value = "分页查询采购订单列表", notes = "分页查询采购订单列表", response = PurchaseOrder.class)
    public ResponseResult<IPage<PurchaseOrder>> list(@RequestBody PurchaseOrderQuery query) {
        LambdaQueryWrapper<PurchaseOrder> wrapper = new LambdaQueryWrapper<>();
        if(query.getPurchaseOrderStatus() != null) {
            wrapper.eq(PurchaseOrder::getPurchaseOrderStatus, query.getPurchaseOrderStatus());
        }

        if(query.getSupplierId() != null) {
            wrapper.or().eq(PurchaseOrder::getSupplierId, query.getSupplierId());
        }
        IPage<PurchaseOrder> result = purchaseOrderService.page(new Page<>(query.getPage(), query.getSize()), wrapper);
        return ResponseResult.buildSuccess(result);
    }

    @SaCheckLogin
    @PostMapping
    @ApiOperation(value = "新增采购订单", notes = "新增采购订单", response = ResponseResult.class)
    public ResponseResult<Boolean> save(@RequestBody PurchaseOrderSaveCommand purchaseOrderSaveCommand) {
        purchaseOrderService.savePurchaseOrder(purchaseOrderSaveCommand);
        return ResponseResult.buildSuccess();
    }

    @SaCheckLogin
    @GetMapping("/{id}")
    @ApiOperation(value = "通过ID查询采购订单", notes = "通过ID查询采购订单", response = PurchaseOrderResponse.class)
    public ResponseResult<PurchaseOrderResponse> getPurchaseOrderById(@PathVariable Long id) {
        PurchaseOrderResponse purchaseOrderResponse = purchaseOrderService.getPurchaseOrderById(id);
        return ResponseResult.buildSuccess(purchaseOrderResponse);
    }

    @SaCheckLogin
    @PutMapping
    @ApiOperation(value = "更新采购订单", notes = "更新采购订单", response = ResponseResult.class)
    public ResponseResult<Boolean> updatePurchaseOrder(@RequestBody PurchaseOrderSaveCommand purchaseOrderSaveCommand) {
        purchaseOrderService.updatePurchaseOrder(purchaseOrderSaveCommand);
        return ResponseResult.buildSuccess();
    }

    @SaCheckLogin
    @PutMapping("/submit/approve/{id}")
    @ApiOperation(value = "提交采购单去审核", notes = "提交采购单去审核", response = ResponseResult.class)
    public ResponseResult<Boolean> submitPurOrderToApprove(@PathVariable Long id) {
        //1. 进行参数校验
        PurchaseOrder purchaseOrder = purchaseOrderService.getById(id);
        if(purchaseOrder == null || !purchaseOrder.getPurchaseOrderStatus().equals(PurchaseOrderStatusConstants.EDITING)) {
            return ResponseResult.buildError("采购单不存在或者状态不为编辑中");
        }

        boolean updateFlag = purchaseOrderService.updateStatus(id, PurchaseOrderStatusEnum.WAIT_FOR_APPROVE.getCode());
        return ResponseResult.buildByFlag(updateFlag);
    }

    @SaCheckLogin
    @Transactional
    @PutMapping("/approve/{id}/{status}")
    @ApiOperation(value = "审核采购单", notes = "审核采购单", response = ResponseResult.class)
    public ResponseResult<Boolean> approve(@PathVariable Long id, @PathVariable Integer status) {
        //校验参数是否正常
        PurchaseOrderResponse purchaseOrderResponse = purchaseOrderService.getPurchaseOrderById(id);
        if(purchaseOrderResponse == null || !purchaseOrderResponse.getPurchaseOrderStatus().equals(PurchaseOrderStatusConstants.WAIT_FOR_APPROVE)) {
            return ResponseResult.buildError("采购单不存在或者状态不为待审核中");
        }

        boolean resultFlag = PurchaseOrderApproveEnum.PASSED.getCode().equals(status) ?
                purchaseOrderService.updateStatus(id, PurchaseOrderStatusEnum.APPROVED.getCode())
                :
                purchaseOrderService.updateStatus(id, PurchaseOrderStatusEnum.EDITING.getCode());

        //更新采购单状态为待入库
        if(PurchaseOrderApproveEnum.PASSED.getCode().equals(status)) {
            purchaseOrderService.updateStatus(id, PurchaseOrderStatusConstants.WAIT_FOR_INBOUND);

        }
        return ResponseResult.buildByFlag(resultFlag);
    }

    @PutMapping("/receive")
    @ApiOperation(value = "接收采购商发送过来的货物", notes = "更新合格商品数与到货的商品数", response = ResponseResult.class)
    public ResponseResult<Boolean> receive(@RequestBody ReceiveProductCommand receiveProductCommand) {
        //采购单状态判断
        Long purchaseOrderId = null;
        for (ReceiveProductCommand.ReceiveItem receiveItem : receiveProductCommand.getReceiveItems()) {
            Long itemId = receiveItem.getId();
            PurchaseOrderItem purchaseOrderItem = purchaseOrderItemService.getById(itemId);
            if(purchaseOrderItem == null) {
                return ResponseResult.buildError("采购单条目不存在");
            }

            purchaseOrderId = purchaseOrderItem.getPurchaseOrderId();
            PurchaseOrder purchaseOrder = purchaseOrderService.getById(purchaseOrderId);
            if(purchaseOrder == null || !purchaseOrder.getPurchaseOrderStatus().equals(PurchaseOrderStatusConstants.WAIT_FOR_INBOUND)) {
                return ResponseResult.buildError("采购单不为待入库状态");
            }
        }

        //更新采购入库单条目的合格数量和到货数量
        List<PurchaseOrderItem> purchaseOrderItems = dozerUtils.mapList(receiveProductCommand.getReceiveItems(), PurchaseOrderItem.class);
        boolean saveFlag = purchaseOrderItemService.updateBatchById(purchaseOrderItems);

        //更新采购入库单的到货时间
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setId(purchaseOrderId);
        purchaseOrder.setArrivalTime(receiveProductCommand.getArrivalTime());
        boolean updateFlag = purchaseOrderService.updateById(purchaseOrder);

        return ResponseResult.buildByFlag(saveFlag && updateFlag);
    }

    @Transactional
    @PutMapping("/inbound/approve/{id}/{status}")
    @ApiOperation(value = "审核采购单入库", notes = "审核采购单单入库", response = ResponseResult.class)
    public ResponseResult inboundApprove(@PathVariable("id") Long id, @PathVariable("status") Integer status) {
        //0. 审核入库单状态判断
        PurchaseOrder purchaseOrder = purchaseOrderService.getById(id);
        if(purchaseOrder == null || !purchaseOrder.getPurchaseOrderStatus().equals(PurchaseOrderStatusConstants.WAIT_FOR_APPROVE_BY_INBOUND)) {
            return ResponseResult.buildError("采购单不存在或状态不为待审核入库");
        }

        //1. 如果是拒绝，则更新为待入库状态
        if(PurchaseInboundOrderApproveResultConstants.REJECTED.equals(status)) {
            purchaseOrderService.updateStatus(id, PurchaseOrderStatusConstants.WAIT_FOR_INBOUND);

        }else {
            //2. 如果是通过，则需要调用责任链来更新处理一系列信息
            PurchaseOrderResponse purchaseOrderResponse = purchaseOrderService.getPurchaseOrderById(id);
            if(!purchaseOrderResponse.getPurchaseOrderStatus().equals(PurchaseOrderStatusConstants.WAIT_FOR_APPROVE_BY_INBOUND)) {
                return ResponseResult.buildError("采购入库单状态不为待审核入库");
            }
            PurchaseOrderHandler handlerChain = purchaseOrderHandlerFactory.getHandlerChain();
            handlerChain.execute(purchaseOrderResponse);
        }

        return ResponseResult.buildSuccess();
    }
}
