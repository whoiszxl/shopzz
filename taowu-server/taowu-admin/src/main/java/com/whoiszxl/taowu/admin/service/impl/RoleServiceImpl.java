package com.whoiszxl.taowu.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.taowu.admin.cqrs.command.RoleAddCommand;
import com.whoiszxl.taowu.admin.cqrs.command.RoleUpdateCommand;
import com.whoiszxl.taowu.admin.cqrs.response.RoleDetailResponse;
import com.whoiszxl.taowu.admin.entity.AdminRole;
import com.whoiszxl.taowu.admin.entity.Menu;
import com.whoiszxl.taowu.admin.entity.Role;
import com.whoiszxl.taowu.admin.entity.RoleMenu;
import com.whoiszxl.taowu.admin.mapper.RoleMapper;
import com.whoiszxl.taowu.admin.service.IAdminRoleService;
import com.whoiszxl.taowu.admin.service.IMenuService;
import com.whoiszxl.taowu.admin.service.IRoleMenuService;
import com.whoiszxl.taowu.admin.service.IRoleService;
import com.whoiszxl.taowu.common.constants.CommonConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2023-03-02
 */
@Service
@RequiredArgsConstructor
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {


    private final IAdminRoleService adminRoleService;

    private final IRoleMenuService roleMenuService;

    private final RoleMapper roleMapper;

    private final IMenuService menuService;



    @Override
    public Set<String> listRoleByAdminId(Integer adminId) {
        List<Role> roleList = this.lambdaQuery().select(Role::getCode).in(Role::getId, getRoleIdListByAdminId(adminId)).list();
        return roleList.stream().map(Role::getCode).collect(Collectors.toSet());
    }

    @Override
    public Set<Integer> listRoleIdByAdminId(Integer adminId) {
        return new HashSet<>(getRoleIdListByAdminId(adminId));
    }

    private List<Integer> getRoleIdListByAdminId(Integer adminId) {
        List<AdminRole> adminRoleList = adminRoleService.list(Wrappers.<AdminRole>lambdaQuery()
                .eq(AdminRole::getAdminId, adminId));
        List<Integer> roleIdList = adminRoleList.stream().map(AdminRole::getRoleId).collect(Collectors.toList());
        return roleIdList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addRole(RoleAddCommand command) {
        //1. 校验此角色是否在表中存在
        boolean exists = this.lambdaQuery()
                .eq(Role::getName, command.getName())
                .or().eq(Role::getCode, command.getCode()).exists();
        Assert.isFalse(exists, "此角色已存在，请重新添加");

        //2. 新增角色
        Role role = BeanUtil.copyProperties(command, Role.class);
        roleMapper.insert(role);

        //3. 重置角色和菜单的关联信息
        roleMenuService.reset(command.getMenuIds(), role.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateRole(RoleUpdateCommand command) {
        //1. 校验此角色是否在表中存在
        boolean exists = this.lambdaQuery().and(wrapper -> wrapper.eq(Role::getName, command.getName()).or().eq(Role::getCode, command.getCode()))
                .and(wrapper -> wrapper.ne(Role::getId, command.getId())).exists();

        Assert.isFalse(exists, "此角色已存在，请重新添加");

        //2. 更新角色
        Role role = BeanUtil.copyProperties(command, Role.class);
        roleMapper.updateById(role);

        //3. 重置角色和菜单的关联信息
        roleMenuService.reset(command.getMenuIds(), role.getId());
    }

    @Override
    public RoleDetailResponse roleDetail(Integer roleId) {
        Role role = roleMapper.selectById(roleId);
        RoleDetailResponse detailResponse = BeanUtil.copyProperties(role, RoleDetailResponse.class);

        if(StrUtil.equals(CommonConstants.SUPER_ADMIN, detailResponse.getCode())) {
            List<Menu> menuList = menuService.list();
            List<Integer> menuIdList = menuList.stream().map(Menu::getId).collect(Collectors.toList());
            detailResponse.setMenuIds(menuIdList);
        }else {
            List<RoleMenu> roleMenuList = roleMenuService.list(Wrappers.<RoleMenu>lambdaQuery().in(RoleMenu::getRoleId, roleId));
            List<Integer> menuIdList = roleMenuList.stream().map(RoleMenu::getMenuId).collect(Collectors.toList());
            detailResponse.setMenuIds(menuIdList);
        }

        return detailResponse;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void roleDelete(List<Integer> ids) {
        Long count = adminRoleService.lambdaQuery().in(AdminRole::getRoleId, ids).count();
        Assert.isTrue(count == 0, "角色与管理员存在关联，无法删除");
        roleMapper.deleteBatchIds(ids);
        roleMenuService.lambdaUpdate().in(RoleMenu::getRoleId, ids).remove();
    }


    @Override
    public void switchStatus(Integer roleId) {
        roleMapper.switchStatus(roleId);
    }
}
