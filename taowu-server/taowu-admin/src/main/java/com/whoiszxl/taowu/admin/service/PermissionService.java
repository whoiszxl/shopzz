package com.whoiszxl.taowu.admin.service;

import java.util.Set;

/**
 * 权限服务接口
 * @author whoiszxl
 */
public interface PermissionService {

    /**
     * 通过管理员id获取此管理员的所有权限
     * @param memberId 管理员ID
     * @return 权限列表
     */
    Set<String> listPermissionsByAdminId(Integer memberId);

    /**
     * 通过管理员ID拿到管理员的所有角色信息
     * @param memberId 管理员ID
     * @return 角色列表
     */
    Set<String> listRoleByAdminId(Integer memberId);

}
