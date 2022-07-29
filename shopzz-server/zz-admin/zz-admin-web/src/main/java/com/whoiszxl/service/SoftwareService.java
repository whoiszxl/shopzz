package com.whoiszxl.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whoiszxl.entity.Software;

/**
 * <p>
 * 基础组件表 服务类
 * </p>
 *
 * @author whoiszxl
 * @since 2021-08-13
 */
public interface SoftwareService extends IService<Software> {

    /**
     * 通过组件名称获取组件信息
     * @param softwareName 组件名称
     * @return 组件信息
     */
    Software getBySoftwareName(String softwareName);
}
