package com.whoiszxl.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whoiszxl.entity.Config;

/**
 * <p>
 * 基础配置表 服务类
 * </p>
 *
 * @author whoiszxl
 * @since 2021-08-12
 */
public interface ConfigService extends IService<Config> {

    /**
     * 通过key获取配置
     * @param key 键
     * @return 值
     */
    String getByKey(String key);
}
