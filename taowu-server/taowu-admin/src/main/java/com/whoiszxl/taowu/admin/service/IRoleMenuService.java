package com.whoiszxl.taowu.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whoiszxl.taowu.admin.entity.RoleMenu;

import java.util.List;

/**
 * <p>
 * 角色与菜单关联表 服务类
 * </p>
 *
 * @author whoiszxl
 * @since 2023-03-02
 */
public interface IRoleMenuService extends IService<RoleMenu> {

    /**
     * 新增角色和菜单的关联信息
     * @param menuIds 菜单ID集合
     * @param roleId 角色ID
     */
    void reset(List<Integer> menuIds, Integer roleId);
}
