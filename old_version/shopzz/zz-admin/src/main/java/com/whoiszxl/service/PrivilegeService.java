package com.whoiszxl.service;

import com.whoiszxl.entity.Privilege;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 权限配置 服务类
 * </p>
 *
 * @author whoiszxl
 * @since 2021-12-06
 */
public interface PrivilegeService extends IService<Privilege> {

    /**
     * 通过adminId获取到此用户的所有权限
     * @param adminId 管理员ID
     * @return 此用户的所有权限
     */
    List<String> getCurrentAdminAvailPrivilegeList(Integer adminId);
}
