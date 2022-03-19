package com.whoiszxl.factory;

import com.whoiszxl.constants.AccountPeriodConstants;
import com.whoiszxl.entity.query.SettlementQuery;
import com.whoiszxl.factory.handler.SettlementHandler;
import com.whoiszxl.factory.handler.WeekSettlementHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 结算工厂
 *
 * @author whoiszxl
 * @date 2021/7/28
 */
@Component
public class SettlementFactory {

    @Autowired
    private WeekSettlementHandler weekSettlementHandler;

    /**
     * 通过结算周期获取到对应的结算处理器
     * @param settlementQuery 结算周期
     * @return
     */
    public SettlementHandler create(SettlementQuery settlementQuery) {
        if(AccountPeriodConstants.WEEK.equals(settlementQuery.getAccountPeriod())) {
            return weekSettlementHandler;
        }else if(AccountPeriodConstants.MONTH.equals(settlementQuery.getAccountPeriod())) {

        }else if(AccountPeriodConstants.QUARTER.equals(settlementQuery.getAccountPeriod())) {

        }

        return weekSettlementHandler;
    }
}
