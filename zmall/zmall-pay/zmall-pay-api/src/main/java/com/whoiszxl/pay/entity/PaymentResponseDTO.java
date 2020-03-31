package com.whoiszxl.pay.entity;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description:
 * @author: whoiszxl
 * @create: 2020-03-31
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "PaymentResponseDTO", description = "支付响应信息")
public class PaymentResponseDTO<T> implements Serializable {

    private String code = "0"; //系统状态 "0"代表请求成功
    private String msg;
    private String tradeNo;//原始渠道订单号() 聚合平台角度：原始交易号      商户应用角度：聚合平台的订单号
    private String outTradeNo;//商户订单号   聚合平台角度：自己订单号       商户应用角度：自己订单号
    private TradeStatus tradeState;//支付状态
    private T content;

    public static <T> PaymentResponseDTO<T> success(T content) {
        PaymentResponseDTO<T> response = new PaymentResponseDTO<T>();
        response.setContent(content);
        return response;
    }

    public static <T> PaymentResponseDTO<T> validfail(String msg) {
        PaymentResponseDTO<T> response = new PaymentResponseDTO<T>();
        response.setCode("999999");
        response.setMsg(msg);
        return response;
    }

    public static <T> PaymentResponseDTO<T> fail(String msg, String outTradeNo, TradeStatus tradeState) {
        PaymentResponseDTO<T> response = new PaymentResponseDTO<T>();
        response.setCode("999999");
        response.setMsg(msg);
        response.setOutTradeNo(outTradeNo);
        response.setTradeState(tradeState);
        return response;
    }

    public static <T> PaymentResponseDTO<T> success(String msg, String tradeNo, String outTradeNo, TradeStatus tradeState) {
        PaymentResponseDTO<T> response = new PaymentResponseDTO<T>();
        response.setMsg(msg);
        response.setTradeNo(tradeNo);
        response.setOutTradeNo(outTradeNo);
        response.setTradeState(tradeState);
        return response;
    }
    public static <T> PaymentResponseDTO<T> success(String tradeNo, String outTradeNo, TradeStatus tradeState, String msg) {
        PaymentResponseDTO<T> response = new PaymentResponseDTO<T>();
        switch (tradeState){
            case SUCCESS:
                response.setMsg("业务交易支付 明确成功");
                break;
            case FAILED:
                response.setMsg("业务交易支付 明确失败 "+msg);
                break;
            case UNKNOWN:
                response.setMsg("业务交易 状态未知");
                break;
            case USERPAYING:
                response.setMsg("交易新建 等待支付");
                break;
            case REVOKED:
                response.setMsg("交易已撤销");
                break;
        }
        response.setTradeNo(tradeNo);
        response.setOutTradeNo(outTradeNo);
        response.setTradeState(tradeState);
        return response;
    }

}
