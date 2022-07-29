package com.whoiszxl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.entity.SoftwareConfig;
import com.whoiszxl.mapper.SoftwareConfigMapper;
import com.whoiszxl.service.SoftwareConfigService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 基础组件配置表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2021-08-13
 */
@Service
public class SoftwareConfigServiceImpl extends ServiceImpl<SoftwareConfigMapper, SoftwareConfig> implements SoftwareConfigService {


    @Override
    public SoftwareConfig getBySoftwareConfigName(String configName) {
        LambdaQueryWrapper<SoftwareConfig> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SoftwareConfig::getConfigName, configName);
        return this.getOne(queryWrapper);
    }

    @Override
    public List<SoftwareConfig> getlistBySoftwareName(String softwareName) {
        LambdaQueryWrapper<SoftwareConfig> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SoftwareConfig::getSoftwareName, softwareName);
        return this.list(queryWrapper);
    }
}
