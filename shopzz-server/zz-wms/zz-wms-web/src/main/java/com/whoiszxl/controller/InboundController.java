package com.whoiszxl.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.cqrs.query.InboundReturnOrderQuery;
import com.whoiszxl.cqrs.response.InboundReturnOrderResponse;
import com.whoiszxl.dozer.DozerUtils;
import com.whoiszxl.entity.InboundReturnOrder;
import com.whoiszxl.service.InboundReturnOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 退货入库相关接口
 *
 * @author whoiszxl
 * @date 2022/3/18
 */
@RestController
@RequestMapping("/inbound")
@Api(tags = "退货入库相关接口")
public class InboundController {

    @Autowired
    private InboundReturnOrderService inboundReturnOrderService;
    
    @Autowired
    private DozerUtils dozerUtils;

    @SaCheckLogin
    @PostMapping("/return/list")
    @ApiOperation(value = "分页获取退货入库列表", notes = "分页获取退货入库列表", response = InboundReturnOrderResponse.class)
    public ResponseResult<IPage<InboundReturnOrderResponse>> list(@RequestBody InboundReturnOrderQuery query) {
        LambdaQueryWrapper<InboundReturnOrder> wrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(query.getOrderNo())) {
            wrapper.eq(InboundReturnOrder::getOrderNo, query.getOrderNo());
        }
        if(StringUtils.isNotBlank(query.getReceiveName())) {
            wrapper.eq(InboundReturnOrder::getReceiveName, query.getReceiveName());
        }
        if(query.getMemberId() != null) {
            wrapper.eq(InboundReturnOrder::getMemberId, query.getMemberId());
        }
        IPage<InboundReturnOrder> result = inboundReturnOrderService.page(new Page<>(query.getPage(), query.getSize()), wrapper);
        IPage<InboundReturnOrderResponse> finalResult = result.convert(e -> dozerUtils.map(e, InboundReturnOrderResponse.class));
        return ResponseResult.buildSuccess(finalResult);
    }

    @SaCheckLogin
    @GetMapping("/return/{id}")
    @ApiOperation(value = "通过主键ID获取退货入库", notes = "通过主键ID获取退货入库", response = InboundReturnOrderResponse.class)
    public ResponseResult<InboundReturnOrderResponse> getSupplierById(@PathVariable Long id) {
        InboundReturnOrder warehouse = inboundReturnOrderService.getById(id);
        return warehouse == null ? ResponseResult.buildSuccess() : ResponseResult.buildSuccess(dozerUtils.map(warehouse, InboundReturnOrderResponse.class));
    }

    @SaCheckLogin
    @DeleteMapping("/return/{id}")
    @ApiOperation(value = "删除退货入库", notes = "删除退货入库", response = ResponseResult.class)
    public ResponseResult<Boolean> delete(@PathVariable Long id) {
        boolean removeFlag = inboundReturnOrderService.removeById(id);
        return ResponseResult.buildByFlag(removeFlag);
    }
}
