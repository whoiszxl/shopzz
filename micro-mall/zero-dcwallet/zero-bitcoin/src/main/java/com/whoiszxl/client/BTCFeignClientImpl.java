package com.whoiszxl.client;

import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.entity.Currency;
import com.whoiszxl.entity.Recharge;
import com.whoiszxl.entity.response.RechargeResponse;
import com.whoiszxl.enums.UpchainStatusEnum;
import com.whoiszxl.feign.BTCFeignClient;
import com.whoiszxl.service.CurrencyService;
import com.whoiszxl.service.RechargeService;
import com.whoiszxl.utils.AssertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;
import wf.bitcoin.javabitcoindrpcclient.BitcoindRpcClient;

import java.math.BigDecimal;

/**
 * 比特币 feign实现
 *
 * @author whoiszxl
 * @date 2021/7/30
 */
@RestController
public class BTCFeignClientImpl implements BTCFeignClient {

    @Autowired
    private BitcoindRpcClient bitcoinClient;

    @Autowired
    private RechargeService rechargeService;

    @Autowired
    private CurrencyService currencyService;

    @Value("${bitcoin.currencyName}")
    private String currencyName;



    @Override
    public ResponseResult<RechargeResponse> createRecharge(String orderId, String amount) {
        //1. 数据有效性校验
        AssertUtils.hasText(orderId, "订单号不能为空");
        AssertUtils.hasText(amount, "金额不能为空");
        AssertUtils.isDouble(amount, "金额格式错误");

        //2. 判断对应的充值单是否存在
        Recharge checkRecharge = rechargeService.getRechargeByCurrencyNameAndOrderId(currencyName, orderId);
        if(checkRecharge == null) {
            return ResponseResult.buildError("充值单记录已存在");
        }

        //3. 获取到货币信息
        Currency currency = currencyService.getCurrencyByName(currencyName);
        AssertUtils.isNotNull(currency, "数据库未配置货币信息：" + currencyName);

        //4. 构建充值单
        Recharge recharge = new Recharge();
        recharge.setOrderId(orderId);
        recharge.setAmount(new BigDecimal(amount));
        recharge.setCurrencyId(currency.getId());
        recharge.setCurrencyName(currency.getCurrencyName());
        recharge.setUpchainStatus(UpchainStatusEnum.NOT_UPCHAIN.getCode());

        //5. 通过orderId为账号创建一个bitcoin地址
        String newAddress = bitcoinClient.getNewAddress(orderId);
        recharge.setToAddress(newAddress);

        //6. 充值单落库
        rechargeService.save(recharge);

        //7. 二维码数据拼接
        String qrcodeData = newAddress + "?amount=" + amount + "&label=" + orderId;

        return ResponseResult.buildSuccess(new RechargeResponse(newAddress, qrcodeData));
    }
}
