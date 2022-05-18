package com.whoiszxl.controller.api;


import cn.dev33.satoken.annotation.SaCheckLogin;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.cqrs.command.SeckillOrderSubmitCommand;
import com.whoiszxl.service.SeckillService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * C端:秒杀相关接口
 * </p>
 *
 * @author whoiszxl
 * @since 2022-04-19
 */
@RestController
@RequestMapping("/api/seckill")
@Api(tags = "C端:秒杀相关接口")
public class SeckillApiController {

    @Autowired
    private SeckillService seckillService;

    @SaCheckLogin
    @PostMapping("/order/submit")
    @ApiOperation(value = "秒杀下单接口", notes = "秒杀下单接口", response = Long.class)
    public ResponseResult<Long> orderSubmit(@RequestBody SeckillOrderSubmitCommand seckillOrderSubmitCommand) {
        Long orderId = seckillService.orderSubmit(seckillOrderSubmitCommand);
        return ResponseResult.buildSuccess(orderId);
    }

}

