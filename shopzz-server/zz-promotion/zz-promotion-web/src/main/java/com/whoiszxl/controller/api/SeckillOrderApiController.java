package com.whoiszxl.controller.api;


import cn.dev33.satoken.annotation.SaCheckLogin;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.cqrs.command.SeckillOrderResultCommand;
import com.whoiszxl.cqrs.command.SeckillOrderSubmitCommand;
import com.whoiszxl.cqrs.query.SeckillOrderQuery;
import com.whoiszxl.cqrs.response.SeckillOrderResponse;
import com.whoiszxl.dozer.DozerUtils;
import com.whoiszxl.entity.SeckillOrder;
import com.whoiszxl.service.SeckillOrderService;
import com.whoiszxl.service.SeckillService;
import com.whoiszxl.utils.AuthUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * C端:秒杀订单相关接口
 * </p>
 *
 * @author whoiszxl
 * @since 2022-04-19
 */
@RestController
@RequestMapping("/api/seckill/order")
@Api(tags = "C端:秒杀订单相关接口")
public class SeckillOrderApiController {

    @Autowired
    private SeckillService seckillService;

    @Autowired
    private SeckillOrderService seckillOrderService;

    @Autowired
    private DozerUtils dozerUtils;

    @SaCheckLogin
    @PostMapping("/submit")
    @ApiOperation(value = "秒杀下单接口", notes = "秒杀下单接口", response = Long.class)
    public ResponseResult<String> orderSubmit(@RequestBody SeckillOrderSubmitCommand seckillOrderSubmitCommand) {
        String taskKey = seckillService.orderSubmit(seckillOrderSubmitCommand);
        return ResponseResult.buildSuccess(taskKey);
    }

    @SaCheckLogin
    @PostMapping("/result")
    @ApiOperation(value = "异步获取秒杀订单结果", notes = "异步获取秒杀订单结果", response = Long.class)
    public ResponseResult<Long> orderResult(@RequestBody SeckillOrderResultCommand seckillOrderResultCommand) {
        Long orderId = seckillService.orderResult(seckillOrderResultCommand);
        return ResponseResult.buildSuccess(orderId);
    }

    @SaCheckLogin
    @PostMapping("/cancel/{orderId}")
    @ApiOperation(value = "取消订单", notes = "取消订单", response = Boolean.class)
    public ResponseResult<Boolean> orderCancel(@PathVariable Long orderId) {
        boolean cancelFlag = seckillService.orderCancel(orderId);
        return ResponseResult.buildByFlag(cancelFlag);
    }

    @SaCheckLogin
    @PostMapping("/list")
    @ApiOperation(value = "获取秒杀订单列表", notes = "获取秒杀订单列表", response = Long.class)
    public ResponseResult<IPage<SeckillOrderResponse>> orderList(@RequestBody SeckillOrderQuery seckillOrderQuery) {
        Long memberId = AuthUtils.getMemberId();
        LambdaQueryWrapper<SeckillOrder> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SeckillOrder::getMemberId, memberId);

        if(StringUtils.isNotBlank(seckillOrderQuery.getSkuName())) {
            queryWrapper.like(SeckillOrder::getSkuName, "%" + seckillOrderQuery.getSkuName() + "%");
        }

        IPage<SeckillOrder> pageResult = seckillOrderService.page(new Page<>(seckillOrderQuery.getPage(), seckillOrderQuery.getSize()), queryWrapper);

        IPage<SeckillOrderResponse> result = pageResult.convert(e -> dozerUtils.map(e, SeckillOrderResponse.class));
        return ResponseResult.buildSuccess(result);
    }

}

