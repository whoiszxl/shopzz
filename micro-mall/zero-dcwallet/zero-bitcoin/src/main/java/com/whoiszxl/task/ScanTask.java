package com.whoiszxl.task;

import com.whoiszxl.entity.Currency;
import com.whoiszxl.entity.Height;
import com.whoiszxl.entity.Recharge;
import com.whoiszxl.enums.UpchainStatusEnum;
import com.whoiszxl.service.CurrencyService;
import com.whoiszxl.service.HeightService;
import com.whoiszxl.service.RechargeService;
import com.whoiszxl.utils.AssertUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import wf.bitcoin.javabitcoindrpcclient.BitcoindRpcClient;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class ScanTask {

    @Value("${bitcoin.currencyName}")
    private String currencyName;

    @Autowired
    private BitcoindRpcClient bitcoinClient;

    @Autowired
    private RechargeService rechargeService;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private HeightService heightService;

    /**
     * 扫描链上的交易是否和数据库中的充值单是否匹配，如果匹配则修改对应状态。
     * 在最近的250个区块的出块时间一般平均为10分钟，所以定时任务运行的时间可以稍微拉长一些，降低服务器与节点的压力。
     * 测试链使用4秒间隔，主网使用5分钟间隔（300 * 1000）。
     * https://txstreet.com/
     */
    @Scheduled(fixedDelay = 4 * 1000)
    public void scanOrder() {
        //获取当前货币的配置信息
        Currency bitcoinInfo = currencyService.getCurrencyByName(currencyName);
        AssertUtils.isNotNull(bitcoinInfo, "数据库未配置货币信息：" + currencyName);

        //获取到当前与网络区块高度
        int networkBlockHeight = bitcoinClient.getBlockCount();
        Height heightObj = rechargeService.getCurrentHeight(currencyName);
        if(heightObj == null) {
            Height height = new Height();
            height.setCurrencyId(bitcoinInfo.getId());
            height.setCurrencyName(bitcoinInfo.getCurrencyName());
            height.setHeight(networkBlockHeight);
            heightService.save(height);
            return;
        }

        Integer currentHeight = heightObj.getHeight();

        //相隔1个区块不进行扫描
        AssertUtils.isFalse(networkBlockHeight - currentHeight <= 1, "不存在需要扫描的区块");

        //扫描区块中的交易
        for(Integer i = currentHeight + 1; i <= networkBlockHeight; i++) {
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
                if(CollectionUtils.isEmpty(outs)) {
                    continue;
                }

                //遍历交易输出集
                for (BitcoindRpcClient.RawTransaction.Out out : outs) {
                    //判断公钥脚本是否有效
                    if(out.scriptPubKey() == null) {
                        continue;
                    }

                    //拿到地址在数据库判断记录是否存在
                    if(CollectionUtils.isEmpty(out.scriptPubKey().addresses())) {
                        continue;
                    }

                    BigDecimal chainAmount = out.value();
                    String address = out.scriptPubKey().addresses().get(0);
                    Recharge recharge = rechargeService.getRechargeByAddressAndCurrencyName(address, currencyName);
                    if(recharge == null) {
                        continue;
                    }
                    if(chainAmount.compareTo(recharge.getAmount()) < 0) {
                        //TODO 暂不做累加充值
                        log.info("充值金额小于订单金额 ....");
                        continue;
                    }

                    //更新recharge表
                    recharge.setTxHash(txId);
                    recharge.setCurrentConfirm(transaction.confirmations());
                    recharge.setHeight(i);
                    recharge.setUpdatedAt(new Date());
                    recharge.setUpchainAt(block.time());
                    if(block.confirmations() >= bitcoinInfo.getConfirms()) {
                        recharge.setUpchainStatus(UpchainStatusEnum.SUCCESS.getCode());
                        recharge.setUpchainSuccessAt(transaction.time());
                    }else {
                        recharge.setUpchainStatus(UpchainStatusEnum.WAITING_CONFIRM.getCode());
                    }
                    rechargeService.updateById(recharge);
                }

            }

        }

        //更新区块高度
        heightObj.setHeight(networkBlockHeight);
        heightService.updateById(heightObj);

    }

    /**
     * 确认交易，将数据库中状态为待确认的充值单再次去链上查询是否确认数超过了配置确认数。
     * 在最近的250个区块的出块时间一般平均为10分钟，所以定时任务运行的时间可以稍微拉长一些，降低服务器与节点的压力。
     * 测试链使用5秒间隔，主网则使用10分钟间隔（600 * 1000）。
     * https://txstreet.com/
     */
    @Scheduled(fixedDelay = 5000)
    public void confirmTx() {
        //0. 获取当前货币的配置信息
        Currency bitcoinInfo = currencyService.getCurrencyByName(currencyName);
        AssertUtils.isNotNull(bitcoinInfo, "数据库未配置货币信息：" + currencyName);

        //1. 查询到所有待确认的充值单
        List<Recharge> waitConfirmRecharge = rechargeService.getWaitConfirmRecharge(currencyName);
        AssertUtils.isNotNull(waitConfirmRecharge, "不存在待确认的充值单");

        //2. 遍历库中交易进行判断是否成功
        for (Recharge recharge : waitConfirmRecharge) {
            BitcoindRpcClient.RawTransaction transaction = bitcoinClient.getRawTransaction(recharge.getTxHash());

            //如果链上交易确认数大于等于配置的确认数，则更新充值单为成功并更新上链成功时间，否则只更新当前确认数。
            if(transaction.confirmations() >= bitcoinInfo.getConfirms()) {
                recharge.setUpchainStatus(UpchainStatusEnum.SUCCESS.getCode());
                recharge.setUpchainSuccessAt(new Date());
            }
            recharge.setCurrentConfirm(transaction.confirmations());
            recharge.setUpdatedAt(new Date());

            rechargeService.saveRecharge(recharge);
        }
    }
}
