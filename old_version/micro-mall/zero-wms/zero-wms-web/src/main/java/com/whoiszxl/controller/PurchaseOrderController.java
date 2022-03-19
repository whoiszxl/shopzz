package com.whoiszxl.controller;


import cn.dev33.satoken.annotation.SaCheckLogin;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.constants.PurchaseInboundOrderApproveResultConstants;
import com.whoiszxl.constants.PurchaseOrderStatusConstants;
import com.whoiszxl.dto.PurchaseOrderDTO;
import com.whoiszxl.entity.ProductAllocation;
import com.whoiszxl.entity.PurchaseInboundOnItem;
import com.whoiszxl.entity.PurchaseOrder;
import com.whoiszxl.entity.PurchaseOrderItem;
import com.whoiszxl.entity.query.PurchaseOrderQuery;
import com.whoiszxl.entity.vo.PurchaseInboundOnItemVO;
import com.whoiszxl.entity.vo.PurchaseOrderVO;
import com.whoiszxl.entity.vo.ReceiveProductVO;
import com.whoiszxl.enums.PurchaseOrderApproveEnum;
import com.whoiszxl.enums.PurchaseOrderStatusEnum;
import com.whoiszxl.factory.PurchaseOrderHandlerFactory;
import com.whoiszxl.factory.handler.PurchaseOrderHandler;
import com.whoiszxl.service.ProductAllocationService;
import com.whoiszxl.service.PurchaseInboundOnItemService;
import com.whoiszxl.service.PurchaseOrderItemService;
import com.whoiszxl.service.PurchaseOrderService;
import com.whoiszxl.utils.BeanCopierUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 采购订单表 前端控制器
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-19
 */
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/purchase-order")
@Api(tags = "采购订单相关接口")
public class PurchaseOrderController {

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @Autowired
    private PurchaseOrderItemService purchaseOrderItemService;

    @Autowired
    private ProductAllocationService productAllocationService;

    @Autowired
    private PurchaseInboundOnItemService purchaseInboundOnItemService;

    @Autowired
    private PurchaseOrderHandlerFactory handlerFactory;
    
    @SaCheckLogin
    //@SaCheckPermission("wms:purchase:list")
    @GetMapping
    @ApiOperation(value = "分页查询采购订单列表", notes = "分页查询采购订单列表", response = PurchaseOrder.class)
    public ResponseResult<IPage<PurchaseOrder>> list(PurchaseOrderQuery query) {
        QueryWrapper<PurchaseOrder> wrapper = new QueryWrapper<>();
        if(query.getPurchaseOrderStatus() != null) {
            wrapper.eq("purchase_order_status", query.getPurchaseOrderStatus());
        }
        if(query.getSupplierId() != null) {
            wrapper.or().eq("supplier_id", query.getSupplierId());
        }
        IPage<PurchaseOrder> result = purchaseOrderService.page(new Page<>(query.getPage(), query.getSize()), wrapper);
        return ResponseResult.buildSuccess(result);
    }

    @SaCheckLogin
    @PostMapping
    @ApiOperation(value = "新增采购订单", notes = "新增采购订单", response = ResponseResult.class)
    public ResponseResult<Boolean> save(@RequestBody PurchaseOrderVO purchaseOrderVO) {
        boolean saveFlag = purchaseOrderService.savePurchaseOrder(purchaseOrderVO);
        return ResponseResult.buildByFlag(saveFlag);
    }

    @SaCheckLogin
    @GetMapping("/{id}")
    @ApiOperation(value = "通过ID查询采购订单", notes = "通过ID查询采购订单", response = PurchaseOrderVO.class)
    public ResponseResult<PurchaseOrderVO> getPurchaseOrderById(@PathVariable Long id) {
        PurchaseOrderDTO purchaseOrderDto = purchaseOrderService.getPurchaseOrderById(id);
        return ResponseResult.buildSuccess(purchaseOrderDto.clone(PurchaseOrderVO.class));
    }

    @SaCheckLogin
    @PutMapping
    @ApiOperation(value = "更新采购订单", notes = "更新采购订单", response = ResponseResult.class)
    public ResponseResult<Boolean> updatePurchaseOrder(@RequestBody PurchaseOrderVO purchaseOrderVO) {
        Boolean updateFlag = purchaseOrderService.updatePurchaseOrder(purchaseOrderVO);
        return ResponseResult.buildByFlag(updateFlag);
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
        PurchaseOrderDTO purchaseOrderDTO = purchaseOrderService.getPurchaseOrderById(id);
        if(purchaseOrderDTO == null || !purchaseOrderDTO.getPurchaseOrderStatus().equals(PurchaseOrderStatusConstants.WAIT_FOR_APPROVE)) {
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
    public ResponseResult<Boolean> receive(@RequestBody ReceiveProductVO receiveProducts) {
        //采购单状态判断
        Long purchaseOrderId = null;
        for (ReceiveProductVO.ReceiveItem receiveItem : receiveProducts.getReceiveItems()) {
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
        List<PurchaseOrderItem> purchaseOrderItems = BeanCopierUtils.copyListProperties(receiveProducts.getReceiveItems(), PurchaseOrderItem::new);
        boolean saveFlag = purchaseOrderItemService.updateBatchById(purchaseOrderItems);

        //更新采购入库单的到货时间
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setId(purchaseOrderId);
        purchaseOrder.setArrivalTime(receiveProducts.getArrivalTime());
        boolean updateFlag = purchaseOrderService.updateById(purchaseOrder);

        return ResponseResult.buildByFlag(saveFlag && updateFlag);
    }


    @PutMapping("/submit/inbound/approve/{id}")
    @ApiOperation(value = "提交采购入库单的审核", notes = "提交采购入库单的审核", response = ResponseResult.class)
    public ResponseResult submitInboundOrderToApprove(@PathVariable("id") Long id) {
        PurchaseOrder purchaseOrder = purchaseOrderService.getById(id);
        if(purchaseOrder == null || !purchaseOrder.getPurchaseOrderStatus().equals(PurchaseOrderStatusConstants.WAIT_FOR_INBOUND)) {
            return ResponseResult.buildError("采购单不存在或状态不为待入库");
        }

        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatusConstants.WAIT_FOR_APPROVE_BY_INBOUND);
        boolean updateFlag = purchaseOrderService.updateById(purchaseOrder);
        return ResponseResult.buildByFlag(updateFlag);
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
            PurchaseOrderDTO purchaseOrderDTO = purchaseOrderService.getPurchaseOrderById(id);
            if(!purchaseOrderDTO.getPurchaseOrderStatus().equals(PurchaseOrderStatusConstants.WAIT_FOR_APPROVE_BY_INBOUND)) {
                return ResponseResult.buildError("采购入库单状态不为待审核入库");
            }
            PurchaseOrderHandler handlerChain = handlerFactory.getHandlerChain();
            handlerChain.execute(purchaseOrderDTO);
        }

        return ResponseResult.buildSuccess();
    }

    @PutMapping("/shelves/on")
    @ApiOperation(value = "批量新增采购入库单的上架条目", notes = "批量新增采购入库单的上架条目", response = ResponseResult.class)
    public ResponseResult<Boolean> batchOnShelves(@RequestBody List<PurchaseInboundOnItemVO> putOnItems) {
        //采购入库单状态判断
        for (PurchaseInboundOnItemVO putOnItem : putOnItems) {
            Long purchaseOrderItemId = putOnItem.getPurchaseOrderItemId();
            PurchaseOrderItem purchaseOrderItem = purchaseOrderItemService.getById(purchaseOrderItemId);
            if(purchaseOrderItem == null) {
                return ResponseResult.buildError("采购单条目不存在");
            }

            ProductAllocation productAllocation = productAllocationService.getById(putOnItem.getProductAllocationId());
            if(productAllocation == null) {
                return ResponseResult.buildError("采购单条目上架的货位不存在");
            }

            if(putOnItem.getPutOnShelvesCount() > purchaseOrderItem.getPurchaseQuantity()) {
                return ResponseResult.buildError(putOnItem.getProductSkuId() + "上架条目数量大于采购数量");
            }
        }

        List<PurchaseInboundOnItem> items = BeanCopierUtils.copyListProperties(putOnItems, PurchaseInboundOnItem::new);
        boolean saveFlag = purchaseInboundOnItemService.saveBatch(items);
        return ResponseResult.buildByFlag(saveFlag);
    }

}

