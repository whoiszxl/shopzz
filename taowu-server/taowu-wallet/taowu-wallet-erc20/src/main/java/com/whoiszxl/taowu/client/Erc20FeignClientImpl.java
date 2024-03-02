package com.whoiszxl.taowu.client;

import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.common.EthereumAddress;
import com.whoiszxl.entity.Currency;
import com.whoiszxl.entity.CurrencyAccount;
import com.whoiszxl.entity.response.RechargeResponse;
import com.whoiszxl.feign.Erc20FeignClient;
import com.whoiszxl.service.CurrencyAccountService;
import com.whoiszxl.service.CurrencyService;
import com.whoiszxl.service.DcPayInfoService;
import com.whoiszxl.service.EthereumService;
import com.whoiszxl.taowu.common.EthereumAddress;
import com.whoiszxl.taowu.common.entity.ResponseResult;
import com.whoiszxl.taowu.common.utils.AssertUtils;
import com.whoiszxl.taowu.entity.Currency;
import com.whoiszxl.taowu.entity.CurrencyAccount;
import com.whoiszxl.taowu.entity.response.RechargeResponse;
import com.whoiszxl.taowu.service.CurrencyAccountService;
import com.whoiszxl.taowu.service.CurrencyService;
import com.whoiszxl.taowu.service.DcPayInfoService;
import com.whoiszxl.taowu.service.EthereumService;
import com.whoiszxl.utils.AssertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.utils.Convert;

import java.math.BigDecimal;

/**
 * shopzz代币 feign接口实现
 *
 * @author whoiszxl
 * @date 2021/8/3
 */
@RestController
public class Erc20FeignClientImpl implements Erc20FeignClient {

    @Value("${ethereum.currencyName}")
    private String currencyName;

    @Autowired
    private EthereumService ethereumService;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private DcPayInfoService dcPayInfoService;

    @Autowired
    private CurrencyAccountService currencyAccountService;

    @Override
    @Transactional
    public ResponseResult<RechargeResponse> giveAddress(String orderId, String amount) {
        //1. 获取货币信息
        Currency ethInfo = currencyService.getCurrencyByName(currencyName);
        AssertUtils.isNotNull(ethInfo, "数据库未配置货币信息：" + currencyName);

        //2. 通过keystore的方式生成一个以太坊地址
        EthereumAddress ethereumAddress = ethereumService.createAddressByFile();
        String qrCodeData = getQrCodeData(amount, ethereumAddress.getAddress());

        //3. 保存keystore文件与地址的对应关系
        CurrencyAccount account = new CurrencyAccount();
        account.setAddress(ethereumAddress.getAddress());
        account.setCurrencyId(ethInfo.getId());
        account.setCurrencyName(ethInfo.getCurrencyName());
        account.setKeystoreName(ethereumAddress.getKeystoreName());
        account.setMnemonic(ethereumAddress.getMnemonic());
        currencyAccountService.save(account);

        return ResponseResult.buildSuccess(new RechargeResponse(ethInfo.getId(), ethereumAddress.getAddress(), qrCodeData));
    }

    /**
     * 通过金额与地址获取二维码的数据
     * @param amount 金额
     * @param address 地址
     * @return 二维码支付数据
     */
    private String getQrCodeData(String amount, String address) {
        BigDecimal amountWei = Convert.toWei(amount, Convert.Unit.ETHER);
        String qrcodeData = "ethereum:" + address + "?value=" + amountWei.toPlainString();
        return qrcodeData;
    }
}
