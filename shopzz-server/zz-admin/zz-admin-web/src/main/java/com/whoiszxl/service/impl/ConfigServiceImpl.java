package com.whoiszxl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.entity.Config;
import com.whoiszxl.mapper.ConfigMapper;
import com.whoiszxl.service.ConfigService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 基础配置表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2021-08-12
 */
@Service
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, Config> implements ConfigService {

    @Override
    public String getByKey(String configKey) {
        LambdaQueryWrapper<Config> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Config::getConfigKey, configKey);
        Config config = this.getOne(queryWrapper);
        return config.getConfigValue();
    }
}
