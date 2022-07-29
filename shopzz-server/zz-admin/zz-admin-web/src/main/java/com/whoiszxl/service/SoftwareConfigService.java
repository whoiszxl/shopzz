package com.whoiszxl.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whoiszxl.entity.SoftwareConfig;

import java.util.List;

/**
 * <p>
 * 基础组件配置表 服务类
 * </p>
 *
 * @author whoiszxl
 * @since 2021-08-13
 */
public interface SoftwareConfigService extends IService<SoftwareConfig> {

    /**
     * 通过组件配置文件名获取配置信息
     * @param zooCfg
     * @return
     */
    SoftwareConfig getBySoftwareConfigName(String zooCfg);

    /**
     *  通过组件名称获取组件配置文件列表
     * @param softwareName 组件名称
     * @return 组件配置文件列表
     */
    List<SoftwareConfig> getlistBySoftwareName(String softwareName);
}
