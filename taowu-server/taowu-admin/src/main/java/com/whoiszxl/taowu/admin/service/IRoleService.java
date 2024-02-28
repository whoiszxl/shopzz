package com.whoiszxl.taowu.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whoiszxl.taowu.admin.cqrs.command.RoleAddCommand;
import com.whoiszxl.taowu.admin.cqrs.command.RoleUpdateCommand;
import com.whoiszxl.taowu.admin.cqrs.response.RoleDetailResponse;
import com.whoiszxl.taowu.admin.entity.Role;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 角色 服务类
 * </p>
 *
 * @author whoiszxl
 * @since 2023-03-02
 */
public interface IRoleService extends IService<Role> {

    /**
     * 获取此用户下的所有角色
     * @param adminId 管理员ID
     * @return 所有角色CODE
     */
    Set<String> listRoleByAdminId(Integer adminId);

    /**
     * 获取此用户下的所有角色ID
     * @param adminId 管理员ID
     * @return 所有角色ID
     */
    Set<Integer> listRoleIdByAdminId(Integer adminId);

    /**
     * 添加角色
     * @param command 角色添加参数
     * @return 是否添加成功
     */
    void addRole(RoleAddCommand command);

    /**
     * 更新角色
     * @param command 角色更新参数
     * @return 是否更新成功
     */
    void updateRole(RoleUpdateCommand command);

    /**
     * 获取角色详情
     * @param roleId 角色ID
     * @return 角色详情
     */
    RoleDetailResponse roleDetail(Integer roleId);

    /**
     * 批量删除角色
     * @param ids 角色id集合
     * @return 是否删除成功
     */
    void roleDelete(List<Integer> ids);

    /**
     * 切换角色状态
     * @param roleId 角色ID
     * @return 是否切换成功
     */
    void switchStatus(Integer roleId);
}
