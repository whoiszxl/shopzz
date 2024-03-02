package com.whoiszxl.taowu.task;

import com.whoiszxl.core.entity.ResponseResult;
import com.whoiszxl.core.enums.UpchainStatusEnum;
import com.whoiszxl.core.utils.AssertUtils;
import com.whoiszxl.entity.Currency;
import com.whoiszxl.entity.Height;
import com.whoiszxl.entity.PayInfoDc;
import com.whoiszxl.feign.OrderFeignClient;
import com.whoiszxl.service.CurrencyService;
import com.whoiszxl.service.DcPayInfoService;
import com.whoiszxl.service.HeightService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import wf.bitcoin.javabitcoindrpcclient.BitcoindRpcClient;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class BtcScanTask {


    @Value("${ethereum.currencyName}")
    private String currencyName;

    private final BitcoindRpcClient bitcoinClient;

    private final CurrencyService currencyService;

    private final DcPayInfoService dcPayInfoService;

    private final HeightService heightService;

    private final OrderFeignClient orderFeignClient;

    /**
     * 扫描链上的交易是否和数据库中的支付单是否匹配，如果匹配则修改对应状态。
     * 在最近的300个区块的出块时间一般平均为15秒。
     * 定时任务使用10秒间隔（10 * 1000）。
     * https://txstreet.com/
     */
    @Scheduled(fixedDelay = 60 * 1000)
    public void scanOrder() {
        //1. 获取当前货币的配置信息
        Currency btcInfo = currencyService.getCurrencyByName(currencyName);
        AssertUtils.isNotNull(btcInfo, "数据库未配置货币信息：" + currencyName);

        //2. 获取到当前与网络区块高度

        //获取到当前与网络区块高度
        int networkBlockHeight = bitcoinClient.getBlockCount();
        Height heightObj = heightService.getHeightByCurrencyName(currencyName);
        if (heightObj == null) {
            Height height = new Height();
            height.setCurrencyId(btcInfo.getId());
            height.setCurrencyName(btcInfo.getCurrencyName());
            height.setCurrentHeight((long) networkBlockHeight);
            heightService.save(height);
            return;
        }

        int currentHeight = heightObj.getCurrentHeight().intValue();

        //3. 相隔1个区块不进行扫描
        if (networkBlockHeight - currentHeight <= 1) {
            log.info("相隔1个区块不进行扫描");
            return;
        }

        //4. 遍历扫描区块中的交易
        for (Integer i = currentHeight + 1; i <= networkBlockHeight; i++) {

            //通过区块高度拿到区块Hash，再通过区块Hash拿到区块对象，再从区块对象中拿到交易ID集合
            String blockHash = bitcoinClient.getBlockHash(i);
            BitcoindRpcClient.Block block = bitcoinClient.getBlock(blockHash);
            List<String> txs = block.tx();

            //遍历区块中的所有交易，判断是否在咱们的数据库中
            for (String txId : txs) {
                //通过交易ID获取到交易对象，从交易对象中拿到交易输出，交易输出就是交易的收款方信息。
                BitcoindRpcClient.RawTransaction transaction = bitcoinClient.getRawTransaction(txId);
                List<BitcoindRpcClient.RawTransaction.Out> outs = transaction.vOut();

                //判断交易输出集是否有效
                if (CollectionUtils.isEmpty(outs)) {
                    continue;
                }

                //遍历交易输出集
                for (BitcoindRpcClient.RawTransaction.Out out : outs) {
                    //判断公钥脚本是否有效
                    if (out.scriptPubKey() == null) {
                        continue;
                    }

                    //拿到地址在数据库判断记录是否存在
                    if (CollectionUtils.isEmpty(out.scriptPubKey().addresses())) {
                        continue;
                    }
                    String address = out.scriptPubKey().addresses().get(0);

                    PayInfoDc payInfoDc = dcPayInfoService.getRechargeByAddressAndCurrencyNameAndUpchainStatus(address, currencyName, UpchainStatusEnum.NOT_UPCHAIN.getCode());
                    if (payInfoDc == null) {
                        //log.info("{} 地址不在库中：{}", currencyName, transaction.getTo());
                        continue;
                    }

                    if (out.value().compareTo(payInfoDc.getTotalAmount()) < 0) {
                        log.info("链上充值金额小于订单金额, 订单金额为：{}, 链上充值金额为：{}", payInfoDc.getTotalAmount().toPlainString(), out.value().toPlainString());
                        continue;
                    }

                    //更新recharge表
                    payInfoDc.setTxHash(txId);
                    payInfoDc.setCurrentConfirm(transaction.confirmations().longValue());
                    payInfoDc.setHeight(i.longValue());
                    payInfoDc.setUpdatedAt(new Date());
                    payInfoDc.setUpchainAt(block.time());
                    if (block.confirmations() >= btcInfo.getConfirms()) {
                        payInfoDc.setUpchainStatus(UpchainStatusEnum.SUCCESS.getCode());
                        payInfoDc.setUpchainSuccessAt(transaction.time());
                    } else {
                        payInfoDc.setUpchainStatus(UpchainStatusEnum.WAITING_CONFIRM.getCode());
                    }
                    dcPayInfoService.updateById(payInfoDc);
                }


            }

            //12. 更新区块高度
            heightObj.setCurrentHeight((long) networkBlockHeight);
            heightService.updateById(heightObj);

        }
    }


        /**
         * 确认交易，将数据库中状态为待确认的充值单再次去链上查询是否确认数超过了配置确认数。
         * 在最近的250个区块的出块时间一般平均为10分钟，所以定时任务运行的时间可以稍微拉长一些，降低服务器与节点的压力。
         * 测试链使用5秒间隔，主网则使用10分钟间隔（600 * 1000）。
         * https://txstreet.com/
         */
        @Scheduled(fixedDelay = 5 * 1000)
        public void confirmTx() {
            //0. 获取当前货币的配置信息
            Currency btcInfo = currencyService.getCurrencyByName(currencyName);
            AssertUtils.isNotNull(btcInfo, "数据库未配置货币信息：" + currencyName);

            //1. 查询到所有待确认的支付单
            List<PayInfoDc> waitConfirmRecharge = dcPayInfoService.getWaitConfirmPayInfo(currencyName);
            if (ObjectUtils.isEmpty(waitConfirmRecharge)) {
                log.info("不存在待确认的支付单");
                return;
            }

            //3. 遍历库中交易进行判断是否成功
            List<Long> paySuccessOrderIds = new ArrayList<>();
            for (PayInfoDc payInfoDc : waitConfirmRecharge) {
                BitcoindRpcClient.RawTransaction transaction = bitcoinClient.getRawTransaction(payInfoDc.getTxHash());

                //如果链上交易确认数大于等于配置的确认数，则更新充值单为成功并更新上链成功时间，否则只更新当前确认数。
                if(transaction.confirmations() >= btcInfo.getConfirms()) {
                    payInfoDc.setUpchainStatus(UpchainStatusEnum.SUCCESS.getCode());
                    payInfoDc.setUpchainSuccessAt(new Date());
                }

                payInfoDc.setCurrentConfirm(transaction.confirmations().longValue());
                payInfoDc.setUpdatedAt(new Date());

                dcPayInfoService.updateById(payInfoDc);
                paySuccessOrderIds.add(payInfoDc.getOrderId());
            }

            if (ObjectUtils.isNotEmpty(paySuccessOrderIds)) {
                //成功了发送消息去更新oms_order记录为支付成功
                ResponseResult<Boolean> responseResult = orderFeignClient.notifyDcPaySuccess(paySuccessOrderIds);
                if (!responseResult.isOk()) {
                    log.error("支付成功了，但是更新订单未成功", responseResult.getMessage());
                }
            }
        }

}
