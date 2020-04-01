package com.whoiszxl.pay.controller;

import com.whoiszxl.common.entity.BusinessException;
import com.whoiszxl.common.entity.Result;
import com.whoiszxl.common.utils.AmountUtil;
import com.whoiszxl.pay.entity.PaymentResponseDTO;
import com.whoiszxl.pay.service.PayService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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

    //下单
    @GetMapping("/nativePay")
    public Result nativePay(@RequestParam("orderId") String orderId, @RequestParam("money")Integer money){
        try {
            String amount = AmountUtil.changeF2Y(money.longValue());
            PaymentResponseDTO paymentResponseDTO = payService.nativePay(orderId, amount);
            return Result.success(paymentResponseDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail("下单失败");
    }

    public static void main(String[] args) {
        String encode = new BCryptPasswordEncoder().encode("669731945");
        System.out.println(encode);
    }


    @ApiOperation("支付宝门店下单付款")
    @PostMapping("/createAliPayOrder")
    public void createAlipayOrderForStore(HttpServletRequest request, HttpServletResponse response) throws BusinessException, IOException {

        try {
            String amount = AmountUtil.changeF2Y(100L);
            PaymentResponseDTO<String> paymentResponseDTO = payService.nativePay("100000011", amount);
            //支付宝下单接口响应
            String content = paymentResponseDTO.getContent();
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write(content);//直接将完整的表单html输出到页面
            response.getWriter().flush();
            response.getWriter().close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    @PostMapping("/notifyPay")
    public Result notifyPay(Object obj) {
        System.out.println(obj);
        return Result.success();
    }
}
