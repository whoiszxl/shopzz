package com.whoiszxl.taowu.admin.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.taowu.admin.entity.Menu;
import com.whoiszxl.taowu.admin.entity.RoleMenu;
import com.whoiszxl.taowu.admin.mapper.RoleMenuMapper;
import com.whoiszxl.taowu.admin.service.IMenuService;
import com.whoiszxl.taowu.admin.service.IRoleMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色与菜单关联表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2023-03-02
 */
@Service
@RequiredArgsConstructor
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements IRoleMenuService {

    private final RoleMenuMapper roleMenuMapper;

    private final IMenuService menuService;

    @Override
    public void reset(List<Integer> menuIds, Integer roleId) {
        Long menuCount = menuService.lambdaQuery().in(Menu::getId, menuIds).count();
        Assert.isTrue(menuIds.size() == menuCount, "菜单参数有误");

        this.lambdaUpdate().eq(RoleMenu::getRoleId, roleId).remove();
        List<RoleMenu> roleMenuList = menuIds.stream().map(menuId -> new RoleMenu(roleId, menuId)).collect(Collectors.toList());
        roleMenuMapper.insertBatch(roleMenuList);
    }
}
