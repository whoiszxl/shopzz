package com.whoiszxl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.entity.DcPayInfo;
import com.whoiszxl.enums.UpchainStatusEnum;
import com.whoiszxl.mapper.DcPayInfoMapper;
import com.whoiszxl.service.DcPayInfoService;
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
public class DcPayInfoServiceImpl extends ServiceImpl<DcPayInfoMapper, DcPayInfo> implements DcPayInfoService {


    @Override
    public DcPayInfo getByOrderIdAndMemberId(Long orderId, Long memberId) {
        LambdaQueryWrapper<DcPayInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DcPayInfo::getOrderId, orderId);
        queryWrapper.eq(DcPayInfo::getMemberId, memberId);
        return this.getOne(queryWrapper);
    }

    @Override
    public DcPayInfo getPayInfoByCurrencyNameAndOrderId(String currencyName, String orderId) {
        LambdaQueryWrapper<DcPayInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DcPayInfo::getOrderId, orderId);
        queryWrapper.eq(DcPayInfo::getCurrencyName, currencyName);
        return this.getOne(queryWrapper);
    }

    @Override
    public List<DcPayInfo> getWaitConfirmPayInfo(String currencyName) {
        LambdaQueryWrapper<DcPayInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DcPayInfo::getUpchainStatus, UpchainStatusEnum.WAITING_CONFIRM.getCode());
        queryWrapper.eq(DcPayInfo::getCurrencyName, currencyName);
        return this.list(queryWrapper);
    }

    @Override
    public DcPayInfo getRechargeByAddressAndCurrencyNameAndUpchainStatus(String toAddress, String currencyName, Integer upchainStatus) {
        LambdaQueryWrapper<DcPayInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DcPayInfo::getToAddress, toAddress);
        queryWrapper.eq(DcPayInfo::getCurrencyName, currencyName);
        queryWrapper.eq(DcPayInfo::getUpchainStatus, upchainStatus);
        return this.getOne(queryWrapper);
    }
}
