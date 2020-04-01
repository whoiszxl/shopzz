package com.whoiszxl.pay.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import com.whoiszxl.common.config.MqTopicEnums;
import com.whoiszxl.common.config.RocketMQConfig;
import com.whoiszxl.common.entity.BusinessException;
import com.whoiszxl.common.entity.CommonErrorCode;
import com.whoiszxl.common.listener.RocketMQSender;
import com.whoiszxl.pay.constants.AliCodeConstants;
import com.whoiszxl.pay.entity.PaymentResponseDTO;
import com.whoiszxl.pay.entity.TradeStatus;
import com.whoiszxl.pay.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: whoiszxl
 * @create: 2020-03-31
 **/
@Slf4j
@Service
public class AlipayServiceImpl implements PayService {

    @Autowired
    private RocketMQSender rocketMQSender;

    //TODO 配置支付宝渠道参数，
    String gateway = "https://openapi.alipaydev.com/gateway.do"; //支付宝接口网关地址
    String appId = "2016090900467946";//支付宝应用id
    String rsaPrivateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCvvTsglDzUvBADqVqDZJ0yP0dTbWg9TNDb+WxPAoA2UStRJUgG5WiHedClNjeukizTo3dKH35mTEM5bvQQOEkQJEaPc0LiOzAoFz4nJ+WBxyJXQuk6qsoeITpzp2GKj5TQDSfS/UL4V4T9nkvueJOVGJ4tEYUA0UzQwR+2BBunkG87WD9DiSgakE/yECHpDIWxQy4VeTrrAWO7Tvx5h7yxb0AcVzwlcx40lvLcvxusmgEcIzwA0HdBZdMQazdoXoFXvSz8QppVDWvF36yqXfeptZ4390O+jvYM0/s53OavpVeVvtwmFQ520oRLpKeEAOj9xl1xrTRAInNgMdp+lcrfAgMBAAECggEAO8xL/j3ypQCJAnXx0pu90Ycuofisa49j0jYA4LGdJt5fuvDQnWgaNV8B2/Wi5MfgcPNCd/pXbD+u8DAM5CgC1HdDezRhIdG6BSpDf9dXhN1Zlg+yhL/wnmf4F7pwulE49cJENsruqso8+n+OnspwjV8fE37OnwSY+r98JtcnAdDXEEo6NK5Y6aw0bBDK900o7CPOTgtWk25y6bCLfDSSmyeS2hlfia6ZrrqagbdV2yOdX1r1xx4JL7YrUqM4HP1ohBYK8JmY7Djk7uiwCeBkSnMBSJnMuAJyyfxAU7u/qRiIOWzxLqVDsbK7zBZQDV5vaQOLj5BsuLNgPyU6BCOLwQKBgQDj5GrseCoN3LXcV+8f0QRyV2nJUyZSAa/R6uu74lM/KbOG3qsr2/n2GaIuq6YLn4C0RqTNJoFNdNIZiR7YKgWbzL+EthWTpq0KQBbAQXLZvxMUeBy+HH+adcTZOrFNExhgTv/cA/QAIYPVpArxtwGANNexV7aegE1JKO06cvGP2QKBgQDFahtCjTDAbFtSrMIaqNwJU13ANYQ4Uek2zOdwwFkaBriT+y2sym+c3N61/8Jq/SXgSufZxmHlFXVWVWabNjO7DOm9UhvVtdcpLpDGugHGrubqOvlF1JSs3LrMZ0S5j8hqjqRt4pg8T3KesDWDP07i+4BeGEOuHpvkhwNQiyQ1dwKBgQC2l+fL/CPF45ebKqGf3nsvmjZaZtMHVe6nxRCIA7krnKAJahPAGorW0ocN2d465dvbldapwTZM91L3vtQF4vWHda/mB3aWTS/MTLlt5YK+UMha2Do/YNokykOkwdAZrzkSwXljsY8pI7Fb1gomzbjYHn7Lyzm5GZfTDIW3FS3TUQKBgF9qlZppG7MlrE4dagPwsclgHeQU+b8u+AWgrAx3K5nEvdnogX8OqTusFYjqAVIs4O2zxrQfiplq8be0t081t3MlHqR+1y1IIqKo+xjSNqGNT9bOgkUaJnIWjcLV9wWpVTkLkLX725Dq5X+Fs5eoNbzHypewKqYx7S9bgmKfnfSpAoGBAIklg50aoDnh6F+pl14wxofeu8VXf3o+L52mNGRKW6qXasoNlzPPeoZeETflNQJ8OPsiM/TJlLwZlxdcaIR4AR5f8nnRwJg2GCtuVB6d4VNhJz9UUcJQP+0oU2SdY39wiwo+6gvRtqbuq989Y7sExUp6gvQka/7eRCqjll8PoIW7";//应用私钥
    String format = "JSON";//json格式
    String charest = "utf-8";//编码
    String alipayPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAsb+JyFdHObeXq/aFBm8LQVClG/xZWevyz/+PzTmXp1rM8SgUOV/HbjRAhYNtxDmm++mcc3ZhbyY+AQiocvpEKHwhncYtuwrJuHnxW+ZvW4J6a5iLUpspd1vkkGCswMbFMHBECjUfjRuYWq0KZ5hKR5sMrUdIqXmwgRgyW6/2nFa+N99Vo325vwvTSpY+wv4lB8VgIiRtWJBRwpE322s5rUTIgNsDCT/z6p3uxm+P233ZuWmlBiDOKtlml/bh++l0go9YgBRtQ7UUwbeKQhsec5tX2NiDBfIqWi2IQmSQ77r0m0xAEFDinP603YN2FisnyrKgce24LdBK3QrSUwncBwIDAQAB";//支付宝公钥
    String signtype = "RSA2";//签名算法
    String returnUrl = "https://www.baidu.com";//支付成功跳转的url
    String notifyUrl = "http://whoiszxl.cross.echosite.cn/api/alipay/notifyPay";//支付结果异步通知的url

    @Override
    public PaymentResponseDTO nativePay(String orderId, String money) {

        //构造sdk的客户端对象
        AlipayClient alipayClient = new DefaultAlipayClient(gateway, appId, rsaPrivateKey, format, charest, alipayPublicKey, signtype); //获得初始化的AlipayClient
        //创建API对应的request
        AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();
        AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
        model.setOutTradeNo(orderId);//商户的订单，就是闪聚平台的订单
        model.setTotalAmount(money);//订单金额（元）
        model.setSubject("支付subject");
        model.setBody("支付body");
        model.setProductCode("QUICK_WAP_PAY");//产品代码，固定QUICK_WAP_PAY
        model.setTimeoutExpress("30m");//订单过期时间
        alipayRequest.setBizModel(model);

        alipayRequest.setReturnUrl(returnUrl);
        alipayRequest.setNotifyUrl(notifyUrl);

        try {
            //请求支付宝下单接口,发起http请求
            AlipayTradeWapPayResponse response = alipayClient.pageExecute(alipayRequest);
            PaymentResponseDTO paymentResponseDTO = new PaymentResponseDTO();
            log.info("调用支付宝下单接口，响应内容:{}", response.getBody());
            paymentResponseDTO.setContent(response.getBody());//支付宝的响应结果

            //异步延迟调用MQ，查询支付结果
            rocketMQSender.asyncSend(MqTopicEnums.PAY_QUERY_TOPIC.getName(), orderId, RocketMQConfig.PAY_QUERY, 10);

            return paymentResponseDTO;
        } catch (AlipayApiException e) {
            e.printStackTrace();
            throw new BusinessException(CommonErrorCode.E_400002);
        }
    }

    @Override
    public PaymentResponseDTO queryPayOrderByAli(String orderId) {

        //构造sdk的客户端对象
        AlipayClient alipayClient = new DefaultAlipayClient(gateway, appId, rsaPrivateKey, format, charest, alipayPublicKey, signtype); //获得初始化的AlipayClient

        //构建订单查询对象
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
        model.setOutTradeNo(orderId);//商户订单号
        request.setBizModel(model);

        AlipayTradeQueryResponse response = null;
        try {
            //请求支付宝订单状态查询接口
            response = alipayClient.execute(request);
            //支付宝响应的code，10000表示接口调用成功
            String code = response.getCode();
            if (AliCodeConstants.SUCCESSCODE.equals(code)) {
                String tradeStatusString = response.getTradeStatus();
                //解析支付宝返回的状态，解析成闪聚平台的TradeStatus
                TradeStatus tradeStatus = covertAliTradeStatusToShanjuCode(tradeStatusString);
                //String tradeNo(支付宝订单号), String orderId（闪聚平台的订单号）, TradeStatus tradeState（订单状态）, String msg（返回信息）
                return PaymentResponseDTO.success(response.getTradeNo(), response.getOutTradeNo(), tradeStatus, response.getMsg());
            }

        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        //String msg, String orderId, TradeStatus tradeState
        return PaymentResponseDTO.fail("支付宝订单状态查询失败", orderId, TradeStatus.UNKNOWN);
    }

    //解析支付宝的订单状态为ZMALL的状态
    private TradeStatus covertAliTradeStatusToShanjuCode(String aliTradeStatus) {
        switch (aliTradeStatus) {
            case AliCodeConstants.TRADE_FINISHED:
            case AliCodeConstants.TRADE_SUCCESS:
                return TradeStatus.SUCCESS;//业务交易支付 明确成功
            case AliCodeConstants.TRADE_CLOSED:
                return TradeStatus.REVOKED;//交易已撤销
            case AliCodeConstants.WAIT_BUYER_PAY:
                return TradeStatus.USERPAYING;//交易新建，等待支付
            default:
                return TradeStatus.FAILED;//交易失败
        }
    }
}
