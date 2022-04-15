package com.whoiszxl.task;

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
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

@Slf4j
@Component
public class Erc20ScanTask {

    @Value("${ethereum.currencyName}")
    private String currencyName;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private EthereumService ethereumService;

    @Autowired
    private HeightService heightService;

    @Autowired
    private DcPayInfoService dcPayInfoService;

    @Autowired
    private OrderFeignClient orderFeignClient;

    /**
     * 扫描链上的交易是否和数据库中的充值单是否匹配，如果匹配则修改对应状态。
     * 在最近的300个区块的出块时间一般平均为15秒。
     * 定时任务使用10秒间隔（10 * 1000）。
     * https://txstreet.com/
     */
    @Scheduled(fixedDelay = 10 * 1000)
    public void scanOrder() {
        //获取当前货币的配置信息
        Currency tokenInfo = currencyService.getCurrencyByName(currencyName);
        AssertUtils.isNotNull(tokenInfo, "数据库未配置货币信息：" + currencyName);

        //获取到当前与网络区块高度
        Long networkBlockHeight = ethereumService.getBlockchainHeight();
        Height heightObj = heightService.getHeightByCurrencyName(currencyName);
        if(heightObj == null) {
            Height height = new Height();
            height.setCurrencyId(tokenInfo.getId());
            height.setCurrencyName(tokenInfo.getCurrencyName());
            height.setCurrentHeight(networkBlockHeight);
            heightService.save(height);
            return;
        }

        Long currentHeight = heightObj.getCurrentHeight();

        //3. 相隔1个区块不进行扫描
        if(networkBlockHeight - currentHeight <= 1) {
            return;
        }

        //扫描区块中的交易
        for(Long i = currentHeight + 1; i <= networkBlockHeight; i++) {
            log.info("开始扫描区块：{}", i);
            //通过区块高度获取到区块中的交易信息
            EthBlock.Block block = ethereumService.getBlockByNumber(i);
            List<EthBlock.TransactionResult> transactionResults = block.getTransactions();

            for (EthBlock.TransactionResult transactionResult : transactionResults) {
                EthBlock.TransactionObject transactionObject = (EthBlock.TransactionObject) transactionResult;
                Transaction transaction = transactionObject.get();

                //通过交易Hash获取到交易的回执信息
                TransactionReceipt txReceipt = ethereumService.getTransactionReceipt(transaction.getHash());
                if(txReceipt == null) {
                    continue;
                }

                //判断状态是否是成功(1成功 0失败)
                if(txReceipt.getStatus().equalsIgnoreCase("0x1")) {
                    String input = transaction.getInput();
                    String toContractAddress = transaction.getTo();
                    if(!StringUtils.isEmpty(input) && input.length() >= 138 && tokenInfo.getContractAddress().equalsIgnoreCase(toContractAddress)) {
                        String data = input.substring(0, 9);
                        data = data + input.substring(17);
                        Function function = new Function("transfer",
                                Collections.emptyList(),
                                Arrays.asList(new TypeReference<Address>() {
                                }, new TypeReference<Uint256>() {
                                }));

                        List<Type> params = FunctionReturnDecoder.decode(data, function.getOutputParameters());

                        //获取充币地址和金额
                        String toAddress = params.get(0).getValue().toString();
                        String amount = params.get(1).getValue().toString();

                        BigDecimal amountDecimal = new BigDecimal(amount).movePointLeft(tokenInfo.getCurrencyDecimalsNum());
                        PayInfoDc payInfoDc = dcPayInfoService.getRechargeByAddressAndCurrencyNameAndUpchainStatus(
                                toAddress, currencyName, UpchainStatusEnum.NOT_UPCHAIN.getCode());
                        if(payInfoDc == null) {
                            log.info("{} 地址不在库中：{}", currencyName, transaction.getTo());
                            continue;
                        }

                        if(amountDecimal.compareTo(payInfoDc.getTotalAmount()) < 0) {
                            log.info("链上充值金额小于订单金额, 订单金额为：{}, 链上充值金额为：{}", payInfoDc.getTotalAmount().toPlainString(), amountDecimal.toPlainString());
                            continue;
                        }

                        //判断是否是假充值
                        boolean flag = ethereumService.checkEventLog(i.intValue(), tokenInfo.getContractAddress(), transaction.getHash());
                        if(!flag) {
                            continue;
                        }

                        payInfoDc.setFromAddress(transaction.getFrom());
                        payInfoDc.setTxHash(transaction.getHash());
                        payInfoDc.setCurrentConfirm(transaction.getBlockNumber().subtract(BigInteger.valueOf(i)).longValue());
                        payInfoDc.setHeight(transaction.getBlockNumber().longValue());
                        payInfoDc.setUpchainAt(new Date(block.getTimestamp().longValue()));
                        payInfoDc.setUpdatedAt(new Date());

                        if(i - block.getNumber().intValue() >= tokenInfo.getConfirms()) {
                            payInfoDc.setUpchainStatus(UpchainStatusEnum.SUCCESS.getCode());
                            payInfoDc.setUpchainSuccessAt(new Date(block.getTimestamp().longValue()));
                        }else {
                            payInfoDc.setUpchainStatus(UpchainStatusEnum.WAITING_CONFIRM.getCode());
                        }
                        dcPayInfoService.updateById(payInfoDc);
                    }
                }
            }
        }

        //更新区块高度
        heightObj.setCurrentHeight(networkBlockHeight);
        heightObj.setUpdatedAt(new Date());
        heightService.updateById(heightObj);
    }


    /**
     * 确认交易，将数据库中状态为待确认的充值单再次去链上查询是否确认数超过了配置确认数。
     * 在最近的300个区块的出块时间一般平均为15秒。
     * 定时任务使用15秒间隔（15 * 1000）。
     * https://txstreet.com/
     */
    @Scheduled(fixedDelay = 10 * 1000)
    public void confirmTx() {
        //0. 获取当前货币的配置信息
        Currency tokenInfo = currencyService.getCurrencyByName(currencyName);
        AssertUtils.isNotNull(tokenInfo, "数据库未配置货币信息：" + currencyName);

        //1. 获取当前网络的区块高度
        Long currentHeight = ethereumService.getBlockchainHeight();

        //2. 查询到所有待确认的充值单
        List<PayInfoDc> waitConfirmPayInfo = dcPayInfoService.getWaitConfirmPayInfo(currencyName);
        if(waitConfirmPayInfo == null) {
            return;
        }

        //3. 遍历库中交易进行判断是否成功
        List<Long> paySuccessOrderIds = new ArrayList<>();
        for (PayInfoDc payInfoDc : waitConfirmPayInfo) {
            Transaction transaction = ethereumService.getTransactionByHash(payInfoDc.getTxHash());
            //如果链上交易确认数大于等于配置的确认数，则更新充值单为成功并更新上链成功时间，否则只更新当前确认数。
            if(currentHeight - transaction.getBlockNumber().longValue()  >= tokenInfo.getConfirms()) {
                payInfoDc.setUpchainStatus(UpchainStatusEnum.SUCCESS.getCode());
                payInfoDc.setUpchainSuccessAt(new Date());
            }
            payInfoDc.setCurrentConfirm(currentHeight - transaction.getBlockNumber().longValue());
            payInfoDc.setUpdatedAt(new Date());
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
