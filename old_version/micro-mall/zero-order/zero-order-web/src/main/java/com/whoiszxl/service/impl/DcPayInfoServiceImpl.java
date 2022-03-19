package com.whoiszxl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.whoiszxl.entity.DcPayInfo;
import com.whoiszxl.mapper.DcPayInfoMapper;
import com.whoiszxl.service.DcPayInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
