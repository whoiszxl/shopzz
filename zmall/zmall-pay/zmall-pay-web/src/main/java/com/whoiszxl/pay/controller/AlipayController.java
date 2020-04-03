package com.whoiszxl.pay.controller;

import com.whoiszxl.common.entity.Result;
import com.whoiszxl.common.utils.AmountUtil;
import com.whoiszxl.order.entity.Order;
import com.whoiszxl.order.feign.OrderFeign;
import com.whoiszxl.pay.entity.PaymentResponseDTO;
import com.whoiszxl.pay.service.PayService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @description: 支付宝支付控制器
 * @author: whoiszxl
 * @create: 2020-03-31
 **/
@RestController
@RequestMapping("/pay/alipay")
public class AlipayController {


    @Autowired
    private PayService payService;

    @Resource
    private OrderFeign orderFeign;

    @ApiOperation("支付宝支付")
    @GetMapping("/nativePay")
    public void nativePay(HttpServletResponse response, @RequestParam("orderId") String orderId){
        try {
            //价格需要通过数据库查询
            Result<Order> orderResult = orderFeign.findById(orderId);
            Order order = orderResult.getResult();
            Integer payMoney = order.getPayMoney();
            String finalAmount = AmountUtil.changeF2Y(String.valueOf(payMoney.longValue()));
            PaymentResponseDTO paymentResponseDTO = payService.nativePay(orderId, finalAmount);
            //支付宝下单接口响应
            String content = (String) paymentResponseDTO.getContent();
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write(content);//直接将完整的表单html输出到页面
            response.getWriter().flush();
            response.getWriter().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @RequestMapping("/notifyPay")
    public Result notifyPay(HttpServletRequest request) {
        Map requestParams = request.getParameterMap();
        System.out.println(requestParams);
        return Result.success();
    }
}
