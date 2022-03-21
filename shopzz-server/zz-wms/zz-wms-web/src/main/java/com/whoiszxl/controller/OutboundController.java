package com.whoiszxl.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.cqrs.query.OutboundSellOrderQuery;
import com.whoiszxl.cqrs.response.OutboundSellOrderResponse;
import com.whoiszxl.cqrs.response.WarehouseResponse;
import com.whoiszxl.dozer.DozerUtils;
import com.whoiszxl.entity.OutboundSellOrder;
import com.whoiszxl.service.OutboundSellOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 出库相关接口
 *
 * @author whoiszxl
 * @date 2022/3/18
 */
@RestController
@RequestMapping("/outbound")
@Api(tags = "出库相关接口")
public class OutboundController {


    @Autowired
    private OutboundSellOrderService outboundSellOrderService;

    @Autowired
    private DozerUtils dozerUtils;

    @SaCheckLogin
    @PostMapping("/sell/list")
    @ApiOperation(value = "分页获取销售出库单列表", notes = "分页获取销售出库单列表", response = OutboundSellOrderResponse.class)
    public ResponseResult<IPage<OutboundSellOrderResponse>> list(@RequestBody OutboundSellOrderQuery query) {
        LambdaQueryWrapper<OutboundSellOrder> wrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(query.getOrderNo())) {
            wrapper.eq(OutboundSellOrder::getOrderNo, query.getOrderNo());
        }
        if(StringUtils.isNotBlank(query.getReceiveName())) {
            wrapper.eq(OutboundSellOrder::getReceiveName, query.getReceiveName());
        }
        if(query.getMemberId() != null) {
            wrapper.eq(OutboundSellOrder::getMemberId, query.getMemberId());
        }
        IPage<OutboundSellOrder> result = outboundSellOrderService.page(new Page<>(query.getPage(), query.getSize()), wrapper);
        IPage<OutboundSellOrderResponse> finalResult = result.convert(e -> dozerUtils.map(e, OutboundSellOrderResponse.class));
        return ResponseResult.buildSuccess(finalResult);
    }

    @SaCheckLogin
    @GetMapping("/sell/{id}")
    @ApiOperation(value = "通过主键ID获取销售出库单", notes = "通过主键ID获取销售出库单", response = WarehouseResponse.class)
    public ResponseResult<WarehouseResponse> getSupplierById(@PathVariable Long id) {
        OutboundSellOrder warehouse = outboundSellOrderService.getById(id);
        return warehouse == null ? ResponseResult.buildSuccess() : ResponseResult.buildSuccess(dozerUtils.map(warehouse, WarehouseResponse.class));
    }

    @SaCheckLogin
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除销售出库单", notes = "删除销售出库单", response = ResponseResult.class)
    public ResponseResult<Boolean> delete(@PathVariable Long id) {
        boolean removeFlag = outboundSellOrderService.removeById(id);
        return ResponseResult.buildByFlag(removeFlag);
    }

}
