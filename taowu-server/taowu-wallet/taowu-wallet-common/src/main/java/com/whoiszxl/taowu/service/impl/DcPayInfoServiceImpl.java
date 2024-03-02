package com.whoiszxl.taowu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.core.enums.UpchainStatusEnum;
import com.whoiszxl.taowu.entity.PayInfoDc;
import com.whoiszxl.taowu.mapper.DcPayInfoMapper;
import com.whoiszxl.taowu.service.DcPayInfoService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 数字货币支付信息表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-30
 */
@Service
public class DcPayInfoServiceImpl extends ServiceImpl<DcPayInfoMapper, PayInfoDc> implements DcPayInfoService {


    @Override
    public PayInfoDc getByOrderIdAndMemberId(Long orderId, Long memberId) {
        LambdaQueryWrapper<PayInfoDc> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PayInfoDc::getOrderId, orderId);
        queryWrapper.eq(PayInfoDc::getMemberId, memberId);
        return this.getOne(queryWrapper);
    }

    @Override
    public PayInfoDc getPayInfoByCurrencyNameAndOrderId(String currencyName, String orderId) {
        LambdaQueryWrapper<PayInfoDc> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PayInfoDc::getOrderId, orderId);
        queryWrapper.eq(PayInfoDc::getCurrencyName, currencyName);
        return this.getOne(queryWrapper);
    }

    @Override
    public List<PayInfoDc> getWaitConfirmPayInfo(String currencyName) {
        LambdaQueryWrapper<PayInfoDc> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PayInfoDc::getUpchainStatus, UpchainStatusEnum.WAITING_CONFIRM.getCode());
        queryWrapper.eq(PayInfoDc::getCurrencyName, currencyName);
        return this.list(queryWrapper);
    }

    @Override
    public PayInfoDc getRechargeByAddressAndCurrencyNameAndUpchainStatus(String toAddress, String currencyName, Integer upchainStatus) {
        LambdaQueryWrapper<PayInfoDc> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PayInfoDc::getToAddress, toAddress);
        queryWrapper.eq(PayInfoDc::getCurrencyName, currencyName);
        queryWrapper.eq(PayInfoDc::getUpchainStatus, upchainStatus);
        return this.getOne(queryWrapper);
    }
}
