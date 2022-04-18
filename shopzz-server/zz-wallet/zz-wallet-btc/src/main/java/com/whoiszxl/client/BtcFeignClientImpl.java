package com.whoiszxl.client;

import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.entity.Currency;
import com.whoiszxl.entity.response.RechargeResponse;
import com.whoiszxl.feign.BTCFeignClient;
import com.whoiszxl.service.CurrencyAccountService;
import com.whoiszxl.service.CurrencyService;
import com.whoiszxl.service.DcPayInfoService;
import com.whoiszxl.utils.AssertUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
public class BtcFeignClientImpl implements BTCFeignClient {

    @Value("${ethereum.currencyName}")
    private String currencyName;

    @Autowired
    private BitcoindRpcClient bitcoinClient;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private DcPayInfoService dcPayInfoService;

    @Autowired
    private CurrencyAccountService currencyAccountService;

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
