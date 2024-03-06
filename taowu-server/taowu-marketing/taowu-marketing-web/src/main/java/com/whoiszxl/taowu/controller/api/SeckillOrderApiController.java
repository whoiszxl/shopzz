package com.whoiszxl.taowu.controller.api;


import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whoiszxl.taowu.common.entity.ResponseResult;
import com.whoiszxl.taowu.common.token.TokenHelper;
import com.whoiszxl.taowu.cqrs.command.SeckillOrderResultCommand;
import com.whoiszxl.taowu.cqrs.command.SeckillOrderSubmitCommand;
import com.whoiszxl.taowu.cqrs.query.SeckillOrderQuery;
import com.whoiszxl.taowu.cqrs.response.SeckillOrderResponse;
import com.whoiszxl.taowu.entity.SeckillOrder;
import com.whoiszxl.taowu.service.SeckillOrderService;
import com.whoiszxl.taowu.service.SeckillService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
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
@Tag(name = "C端:秒杀订单相关接口")
@RequiredArgsConstructor
public class SeckillOrderApiController {

    private final SeckillService seckillService;

    private final SeckillOrderService seckillOrderService;

    private final TokenHelper tokenHelper;


    @SaCheckLogin
    @PostMapping("/submit")
    @Operation(summary = "秒杀下单接口", description = "秒杀下单接口")
    public ResponseResult<String> orderSubmit(@RequestBody SeckillOrderSubmitCommand seckillOrderSubmitCommand) {
        String taskKey = seckillService.orderSubmit(seckillOrderSubmitCommand);
        return ResponseResult.buildSuccess(taskKey);
    }

    @SaCheckLogin
    @PostMapping("/result")
    @Operation(summary = "异步获取秒杀订单结果", description = "异步获取秒杀订单结果")
    public ResponseResult<Long> orderResult(@RequestBody SeckillOrderResultCommand seckillOrderResultCommand) {
        Long orderId = seckillService.orderResult(seckillOrderResultCommand);
        return ResponseResult.buildSuccess(orderId);
    }

    @SaCheckLogin
    @PostMapping("/cancel/{orderId}")
    @Operation(summary = "取消订单", description = "取消订单")
    public ResponseResult<Boolean> orderCancel(@PathVariable Long orderId) {
        boolean cancelFlag = seckillService.orderCancel(orderId);
        return ResponseResult.buildByFlag(cancelFlag);
    }

    @SaCheckLogin
    @PostMapping("/list")
    @Operation(summary = "获取秒杀订单列表", description = "获取秒杀订单列表")
    public ResponseResult<IPage<SeckillOrderResponse>> orderList(@RequestBody SeckillOrderQuery seckillOrderQuery) {
        Long memberId = tokenHelper.getAppMemberId();
        LambdaQueryWrapper<SeckillOrder> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SeckillOrder::getMemberId, memberId);

        if(StringUtils.isNotBlank(seckillOrderQuery.getSkuName())) {
            queryWrapper.like(SeckillOrder::getSkuName, "%" + seckillOrderQuery.getSkuName() + "%");
        }

        IPage<SeckillOrder> pageResult = seckillOrderService.page(new Page<>(seckillOrderQuery.getPage(), seckillOrderQuery.getSize()), queryWrapper);

        IPage<SeckillOrderResponse> result = pageResult.convert(e -> BeanUtil.copyProperties(e, SeckillOrderResponse.class));
        return ResponseResult.buildSuccess(result);
    }

}

