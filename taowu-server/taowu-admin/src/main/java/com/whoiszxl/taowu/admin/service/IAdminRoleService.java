package com.whoiszxl.taowu.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whoiszxl.taowu.admin.entity.AdminRole;

import java.util.List;

/**
 * <p>
 * 管理员&角色关联表 服务类
 * </p>
 *
 * @author whoiszxl
 * @since 2023-03-02
 */
public interface IAdminRoleService extends IService<AdminRole> {

    /**
     * 保存管理员的角色信息
     * @param roleIds
     * @param id
     */
    void save(List<Integer> roleIds, Integer id);

    /**
     * 重新设置管理员的角色
     * @param roleIds 角色ID集合
     * @param adminId 管理员ID
     */
    void resetRole(List<Integer> roleIds, Integer adminId);
}
