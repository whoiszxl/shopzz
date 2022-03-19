package com.whoiszxl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.whoiszxl.entity.Height;
import com.whoiszxl.mapper.HeightMapper;
import com.whoiszxl.service.HeightService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 区块高度同步记录 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2021-08-03
 */
@Service
public class HeightServiceImpl extends ServiceImpl<HeightMapper, Height> implements HeightService {


    @Override
    public Height getHeightByCurrencyName(String currencyName) {
        LambdaQueryWrapper<Height> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Height::getCurrencyName, currencyName);
        return this.getOne(queryWrapper);
    }
}
