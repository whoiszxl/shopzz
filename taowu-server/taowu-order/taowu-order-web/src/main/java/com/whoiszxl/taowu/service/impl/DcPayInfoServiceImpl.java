package com.whoiszxl.taowu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.whoiszxl.taowu.entity.PayInfoDc;
import com.whoiszxl.taowu.mapper.DcPayInfoMapper;
import com.whoiszxl.taowu.service.DcPayInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 数字货币支付信息表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2022-04-09
 */
@Service
@RequiredArgsConstructor
public class DcPayInfoServiceImpl extends ServiceImpl<DcPayInfoMapper, PayInfoDc> implements DcPayInfoService {

    @Override
    public PayInfoDc getByOrderIdAndMemberId(Long orderId, Long memberId) {
        LambdaQueryWrapper<PayInfoDc> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PayInfoDc::getOrderId, orderId);
        queryWrapper.eq(PayInfoDc::getMemberId, memberId);
        return this.getOne(queryWrapper);
    }
}
