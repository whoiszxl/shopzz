package com.whoiszxl.taowu.task;

import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.entity.Currency;
import com.whoiszxl.entity.PayInfoDc;
import com.whoiszxl.entity.Height;
import com.whoiszxl.enums.UpchainStatusEnum;
import com.whoiszxl.feign.OrderFeignClient;
import com.whoiszxl.service.CurrencyService;
import com.whoiszxl.service.DcPayInfoService;
import com.whoiszxl.service.EthereumService;
import com.whoiszxl.service.HeightService;
import com.whoiszxl.utils.AssertUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class EthScanTask {


    @Value("${ethereum.currencyName}")
    private String currencyName;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private EthereumService ethereumService;

    @Autowired
    private DcPayInfoService dcPayInfoService;

    @Autowired
    private HeightService heightService;

    @Autowired
    private OrderFeignClient orderFeignClient;

    /**
     * 扫描链上的交易是否和数据库中的支付单是否匹配，如果匹配则修改对应状态。
     * 在最近的300个区块的出块时间一般平均为15秒。
     * 定时任务使用10秒间隔（10 * 1000）。
     * https://txstreet.com/
     */
    @Scheduled(fixedDelay = 60 * 1000)
    public void scanOrder() {
        //1. 获取当前货币的配置信息
        Currency ethInfo = currencyService.getCurrencyByName(currencyName);
        AssertUtils.isNotNull(ethInfo, "数据库未配置货币信息：" + currencyName);

        //2. 获取到当前与网络区块高度
        Long networkBlockHeight = ethereumService.getBlockchainHeight();
        Height heightObj = heightService.getHeightByCurrencyName(currencyName);
        if(heightObj == null) {
            Height height = new Height();
            height.setCurrencyId(ethInfo.getId());
            height.setCurrencyName(ethInfo.getCurrencyName());
            height.setCurrentHeight(networkBlockHeight);
            heightService.save(height);
            return;
        }

        Long currentHeight = heightObj.getCurrentHeight();

        //3. 相隔1个区块不进行扫描
        if(networkBlockHeight - currentHeight <= 1) {
            return;
        }

        //4. 遍历扫描区块中的交易
        for(Long i = currentHeight + 1; i <= networkBlockHeight; i++) {

            //5. 通过区块高度拿到区块，再从区块中拿到交易集合
            EthBlock.Block block = ethereumService.getBlockByNumber(i.longValue());
            List<EthBlock.TransactionResult> transactions = block.getTransactions();

            //6. 遍历交易集合
            for (EthBlock.TransactionResult transactionResult : transactions) {

                //7. 拿到交易集合中的Transaction对象
                EthBlock.TransactionObject transactionObject = (EthBlock.TransactionObject) transactionResult;
                Transaction transaction = transactionObject.get();

                //8. 校验交易有效性
                if(StringUtils.isEmpty(transaction.getTo())) {
                    log.info("交易{}不存在toAddress", transaction.getHash());
                    continue;
                }

                //9. 判断支付单是否存在，判断链上充值金额是否足够支付订单
                BigDecimal amount = Convert.fromWei(transaction.getValue().toString(), Convert.Unit.ETHER);
                PayInfoDc payInfoDc = dcPayInfoService.getRechargeByAddressAndCurrencyNameAndUpchainStatus(transaction.getTo(), currencyName, UpchainStatusEnum.NOT_UPCHAIN.getCode());
                if(payInfoDc == null) {
                    //log.info("{} 地址不在库中：{}", currencyName, transaction.getTo());
                    continue;
                }

                if(amount.compareTo(payInfoDc.getTotalAmount()) < 0) {
                    log.info("链上充值金额小于订单金额, 订单金额为：{}, 链上充值金额为：{}", payInfoDc.getTotalAmount().toPlainString(), amount.toPlainString());
                    continue;
                }

                //10. 填充基础信息
                payInfoDc.setFromAddress(transaction.getFrom());
                payInfoDc.setTxHash(transaction.getHash());
                payInfoDc.setCurrentConfirm(transaction.getBlockNumber().subtract(BigInteger.valueOf(i)).longValue());
                payInfoDc.setHeight(transaction.getBlockNumber().longValue());
                payInfoDc.setUpchainAt(new Date(block.getTimestamp().longValue()));

                //11. 判断是否上链确认成功
                if(i - block.getNumber().intValue() >= ethInfo.getConfirms()) {
                    payInfoDc.setUpchainStatus(UpchainStatusEnum.SUCCESS.getCode());
                    payInfoDc.setUpchainSuccessAt(new Date(block.getTimestamp().longValue()));
                }else {
                    payInfoDc.setUpchainStatus(UpchainStatusEnum.WAITING_CONFIRM.getCode());
                }
                dcPayInfoService.updateById(payInfoDc);
            }

        }

        //12. 更新区块高度
        heightObj.setCurrentHeight(networkBlockHeight);
        heightService.updateById(heightObj);

    }



    /**
     * 确认交易，将数据库中状态为待确认的支付单再次去链上查询是否确认数超过了配置确认数。
     * 在最近的300个区块的出块时间一般平均为15秒。
     * 定时任务使用15秒间隔（15 * 1000）。
     * https://txstreet.com/
     */
    @Scheduled(fixedDelay = 10 * 1000)
    public void confirmTx() {
        //0. 获取当前货币的配置信息
        Currency ethInfo = currencyService.getCurrencyByName(currencyName);
        AssertUtils.isNotNull(ethInfo, "数据库未配置货币信息：" + currencyName);

        //1. 获取当前网络的区块高度
        Long currentHeight = ethereumService.getBlockchainHeight();

        //2. 查询到所有待确认的支付单
        List<PayInfoDc> waitConfirmRecharge = dcPayInfoService.getWaitConfirmPayInfo(currencyName);
        if(ObjectUtils.isEmpty(waitConfirmRecharge)) {
            log.info("不存在待确认的支付单");
            return;
        }

        //3. 遍历库中交易进行判断是否成功
        List<Long> paySuccessOrderIds = new ArrayList<>();
        for (PayInfoDc payInfoDc : waitConfirmRecharge) {
            Transaction transaction = ethereumService.getTransactionByHash(payInfoDc.getTxHash());

            //如果链上交易确认数大于等于配置的确认数，则更新支付单为成功并更新上链成功时间，否则只更新当前确认数。
            if(currentHeight - transaction.getBlockNumber().longValue()  >= ethInfo.getConfirms()) {
                payInfoDc.setUpchainStatus(UpchainStatusEnum.SUCCESS.getCode());
                payInfoDc.setUpchainSuccessAt(new Date());
            }
            payInfoDc.setCurrentConfirm(currentHeight - transaction.getBlockNumber().longValue());
            dcPayInfoService.updateById(payInfoDc);

            paySuccessOrderIds.add(payInfoDc.getOrderId());
        }

        if(ObjectUtils.isNotEmpty(paySuccessOrderIds)) {
            //成功了发送消息去更新oms_order记录为支付成功
            ResponseResult<Boolean> responseResult = orderFeignClient.notifyDcPaySuccess(paySuccessOrderIds);
            if(!responseResult.isOk()) {
                log.error("支付成功了，但是更新订单未成功", responseResult.getMessage());
            }
        }
    }

}
