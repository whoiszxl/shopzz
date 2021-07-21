package com.whoiszxl.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.constant.PurchaseInboundOrderApproveResult;
import com.whoiszxl.constant.PurchaseInboundOrderStatus;
import com.whoiszxl.dto.PurchaseInboundOrderDTO;
import com.whoiszxl.entity.PurchaseInboundOnItem;
import com.whoiszxl.entity.PurchaseInboundOrder;
import com.whoiszxl.entity.PurchaseOrder;
import com.whoiszxl.entity.query.PurchaseInBoundOrderQuery;
import com.whoiszxl.entity.vo.PurchaseInboundOnItemVO;
import com.whoiszxl.entity.vo.PurchaseInboundOrderVO;
import com.whoiszxl.factory.handler.PurchaseInboundOrderHandler;
import com.whoiszxl.factory.PurchaseInboundOrderHandlerFactory;
import com.whoiszxl.service.PurchaseInboundOnItemService;
import com.whoiszxl.service.PurchaseInboundOrderService;
import com.whoiszxl.utils.BeanCopierUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 采购入库订单表 前端控制器
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-20
 */
@RestController
@RequestMapping("/wms/purchase-inbound-order")
@Api(tags = "采购入库订单相关接口")
public class PurchaseInboundOrderController {

    @Autowired
    private PurchaseInboundOrderService purchaseInboundOrderService;

    @Autowired
    private PurchaseInboundOnItemService purchaseInboundOnItemService;

    @Autowired
    private PurchaseInboundOrderHandlerFactory handlerFactory;

    @GetMapping
    @ApiOperation(value = "分页查询采购入库单列表", notes = "分页查询采购入库单列表", response = PurchaseOrder.class)
    public ResponseResult<IPage<PurchaseInboundOrder>> list(PurchaseInBoundOrderQuery query) {
        QueryWrapper<PurchaseInboundOrder> wrapper = new QueryWrapper<>();
        if(query.getPurchaseInboundOrderStatus() != null) {
            wrapper.eq("purchase_inbound_order_status", query.getPurchaseInboundOrderStatus());
        }
        if(query.getSupplierId() != null) {
            wrapper.or().eq("supplier_id", query.getSupplierId());
        }
        IPage<PurchaseInboundOrder> result = purchaseInboundOrderService.page(new Page<>(query.getPage(), query.getSize()), wrapper);
        return ResponseResult.buildSuccess(result);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "通过主键ID查询采购入库单", notes = "通过主键ID查询采购入库单", response = PurchaseInboundOrderVO.class)
    public ResponseResult<PurchaseInboundOrderVO> getPurchaseInboundOrderById(@PathVariable("id") Long id) {
        PurchaseInboundOrderDTO purchaseInboundOrderDTO = purchaseInboundOrderService.getPurchaseInboundOrderById(id);
        return ResponseResult.buildSuccess(purchaseInboundOrderDTO.clone(PurchaseInboundOrderVO.class));
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "更新入库单", notes = "更新入库单", response = ResponseResult.class)
    public ResponseResult<Boolean> update(@RequestBody PurchaseInboundOrderVO purchaseInboundOrderVO) {
        Boolean updateFlag = purchaseInboundOrderService.updatePurchaseInboundOrder(purchaseInboundOrderVO);
        return ResponseResult.buildByFlag(updateFlag);
    }

    @PutMapping("/shelves/on/{id}")
    @ApiOperation(value = "批量新增采购入库单的上架条目", notes = "批量新增采购入库单的上架条目", response = ResponseResult.class)
    public ResponseResult<Boolean> batchOnShelves(@RequestBody List<PurchaseInboundOnItemVO> putOnItems) {
        List<PurchaseInboundOnItem> items = BeanCopierUtils.copyListProperties(putOnItems, PurchaseInboundOnItem::new);
        boolean saveFlag = purchaseInboundOnItemService.saveBatch(items);
        return ResponseResult.buildByFlag(saveFlag);
    }

    @PutMapping("/submit/approve/{id}")
    @ApiOperation(value = "提交采购入库单的审核", notes = "提交采购入库单的审核", response = ResponseResult.class)
    public ResponseResult submitOrderToApprove(@PathVariable("id") Long id) {
        PurchaseInboundOrder purchaseInboundOrder = purchaseInboundOrderService.getById(id);
        if(purchaseInboundOrder == null) {
            return ResponseResult.buildError();
        }

        purchaseInboundOrder.setPurchaseInboundOrderStatus(PurchaseInboundOrderStatus.WAIT_FOR_APPROVE);
        boolean updateFlag = purchaseInboundOrderService.updateById(purchaseInboundOrder);
        return ResponseResult.buildByFlag(updateFlag);
    }

    @PutMapping("/approve/{id}/{status}")
    @ApiOperation(value = "审核入库单", notes = "审核入库单", response = ResponseResult.class)
    public ResponseResult approve(@PathVariable("id") Long id, @PathVariable("status") Integer status) {
        //1. 如果是拒绝，则更新为编辑中状态
        if(PurchaseInboundOrderApproveResult.REJECTED.equals(status)) {
            purchaseInboundOrderService.updateStatus(id, PurchaseInboundOrderStatus.EDITING);

        }else {
            //2. 如果是通过，则需要调用责任链来更新处理一系列信息
            PurchaseInboundOrder purchaseInboundOrder = purchaseInboundOrderService.getById(id);
            PurchaseInboundOrderHandler handlerChain = handlerFactory.getHandlerChain();
            handlerChain.execute(purchaseInboundOrder.clone(PurchaseInboundOrderDTO.class));
        }

        return ResponseResult.buildSuccess();
    }


}

