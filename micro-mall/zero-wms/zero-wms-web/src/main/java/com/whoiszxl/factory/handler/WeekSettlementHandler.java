package com.whoiszxl.factory.handler;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.whoiszxl.constants.AccountPeriodConstants;
import com.whoiszxl.entity.PurchaseOrder;
import com.whoiszxl.entity.PurchaseSettlementOrder;
import com.whoiszxl.entity.PurchaseSupplier;
import com.whoiszxl.service.PurchaseOrderService;
import com.whoiszxl.service.PurchaseSettlementOrderService;
import com.whoiszxl.service.PurchaseSupplierService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 周结算处理器
 *
 * @author whoiszxl
 * @date 2021/7/28
 */
@Slf4j
@Component
public class WeekSettlementHandler implements SettlementHandler {

    @Autowired
    private PurchaseSupplierService purchaseSupplierService;

    @Autowired
    private PurchaseSettlementOrderService purchaseSettlementOrderService;

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @Override
    public boolean execute() {
        //1. 通过结算周期获取到所有的供应商
        List<PurchaseSupplier> supplierList = purchaseSupplierService.list(
                new LambdaQueryWrapper<PurchaseSupplier>().eq(PurchaseSupplier::getAccountPeriod, AccountPeriodConstants.WEEK));

        //2. 遍历供应商，获取到这周的已审核的结算单
        for (PurchaseSupplier supplier : supplierList) {
            Date endTime = new Date();
            Date startTime = DateUtils.addDays(endTime, -7);

            LambdaQueryWrapper<PurchaseSettlementOrder> queryWrapper = new LambdaQueryWrapper();
            queryWrapper.eq(PurchaseSettlementOrder::getSupplierId, supplier.getId());
            queryWrapper.between(PurchaseSettlementOrder::getCreatedAt, startTime, endTime);
            List<PurchaseSettlementOrder> settlementOrderList = purchaseSettlementOrderService.list(queryWrapper);

            BigDecimal totalSettlementAmount = BigDecimal.ZERO;
            for (PurchaseSettlementOrder settlementOrder : settlementOrderList) {
                totalSettlementAmount = totalSettlementAmount.add(settlementOrder.getTotalSettlementAmount());
            }

            payForSupplier(supplier.getBankName(), supplier.getBankAccount(), supplier.getAccountHolder(), totalSettlementAmount);

            //通过供应商ID更新采购单状态为已完成
            purchaseOrderService.updateFinishedBySupplierId(supplier.getId());
        }

        return true;
    }

    /**
     * 给供应商支付结算金额
     * @param bankName 供应商银行名称
     * @param bankAccount 供应商账号
     * @param accountHolder 供应商银行账号开户人
     * @param totalSettlementAmount 结算金额
     */
    private void payForSupplier(String bankName, String bankAccount, String accountHolder, BigDecimal totalSettlementAmount) {
        //TODO 没找到银行转账SDK，考虑支付宝与数字货币实现。
        log.info("支付货款给供应商...");
    }
}
