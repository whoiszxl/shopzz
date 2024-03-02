package com.whoiszxl.taowu.client;

import com.whoiszxl.feign.BTCFeignClient;
import com.whoiszxl.taowu.common.entity.ResponseResult;
import com.whoiszxl.taowu.common.utils.AssertUtils;
import com.whoiszxl.taowu.entity.Currency;
import com.whoiszxl.taowu.entity.response.RechargeResponse;
import com.whoiszxl.taowu.service.CurrencyAccountService;
import com.whoiszxl.taowu.service.CurrencyService;
import com.whoiszxl.taowu.service.DcPayInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import wf.bitcoin.javabitcoindrpcclient.BitcoindRpcClient;

/**
 * 以太币feign接口实现
 *
 * @author whoiszxl
 * @date 2021/8/3
 */
@RestController
@RequiredArgsConstructor
public class BtcFeignClientImpl implements BTCFeignClient {

    @Value("${ethereum.currencyName}")
    private String currencyName;

    private final BitcoindRpcClient bitcoinClient;

    private final CurrencyService currencyService;

    private final DcPayInfoService dcPayInfoService;

    private final CurrencyAccountService currencyAccountService;

    @Override
    @Transactional
    @PostMapping("/createRecharge/{orderId}/{amount}")
    public ResponseResult<RechargeResponse> giveAddress(String orderId, String amount) {
        //1. 获取货币信息
        Currency btcInfo = currencyService.getCurrencyByName(currencyName);
        AssertUtils.isNotNull(btcInfo, "数据库未配置货币信息：" + currencyName);

        //2. 生成bitcoin地址
        String newAddress = bitcoinClient.getNewAddress(orderId);

        return ResponseResult.buildSuccess(new RechargeResponse(btcInfo.getId(), newAddress, amount));
    }
}
