package com.whoiszxl.taowu.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whoiszxl.taowu.admin.cqrs.command.MenuAddCommand;
import com.whoiszxl.taowu.admin.cqrs.command.MenuUpdateCommand;
import com.whoiszxl.taowu.admin.entity.Menu;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 系统菜单 服务类
 * </p>
 *
 * @author whoiszxl
 * @since 2023-03-02
 */
public interface IMenuService extends IService<Menu> {

    /**
     * 通过管理员id获取此管理员的所有权限
     * @param adminId 管理员ID
     * @return 权限列表
     */
    Set<String> listPermissionsByAdminId(Integer adminId);

    /**
     * 添加菜单
     * @param command 添加参数
     */
    void addMenu(MenuAddCommand command);

    /**
     * 更新菜单
     * @param command 更新参数
     */
    void updateMenu(MenuUpdateCommand command);

    /**
     *删除菜单
     * @param ids 菜单ID集合
     */
    void deleteMenu(List<Integer> ids);

}
