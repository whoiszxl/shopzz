package com.whoiszxl.controller;


import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.client.DispatchClient;
import com.whoiszxl.dto.PurchaseOrderDTO;
import com.whoiszxl.entity.PurchaseOrder;
import com.whoiszxl.entity.query.PurchaseOrderQuery;
import com.whoiszxl.entity.vo.PurchaseOrderVO;
import com.whoiszxl.enums.PurchaseOrderApproveEnum;
import com.whoiszxl.enums.PurchaseOrderStatusEnum;
import com.whoiszxl.service.PurchaseOrderService;
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
    private DispatchClient dispatchClient;

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
    public ResponseResult<Boolean> submitOrderToApprove(@PathVariable Long id) {
        boolean updateFlag = purchaseOrderService.updateStatus(id, PurchaseOrderStatusEnum.WAIT_FOR_APPROVE.getCode());
        return ResponseResult.buildByFlag(updateFlag);
    }

    @SaCheckLogin
    @Transactional
    @PutMapping("/approve/{id}/{status}")
    @ApiOperation(value = "审核采购单", notes = "审核采购单", response = ResponseResult.class)
    public ResponseResult<Boolean> approve(@PathVariable Long id, @PathVariable Integer status) {
        boolean resultFlag = PurchaseOrderApproveEnum.PASSED.getCode().equals(status) ?
                purchaseOrderService.updateStatus(id, PurchaseOrderStatusEnum.APPROVED.getCode())
                :
                purchaseOrderService.updateStatus(id, PurchaseOrderStatusEnum.EDITING.getCode());

        //通过后发送此采购单到调度中心，调度中心包装成入库单到WMS系统中
        if(PurchaseOrderApproveEnum.PASSED.getCode().equals(status)) {
            PurchaseOrderDTO purchaseOrderDTO = purchaseOrderService.getPurchaseOrderById(id);
            dispatchClient.dispatchPurchaseInBound(purchaseOrderDTO);
        }

        return ResponseResult.buildByFlag(resultFlag);
    }

}

