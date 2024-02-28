package com.whoiszxl.taowu.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.taowu.admin.cqrs.command.MenuAddCommand;
import com.whoiszxl.taowu.admin.cqrs.command.MenuUpdateCommand;
import com.whoiszxl.taowu.admin.entity.Menu;
import com.whoiszxl.taowu.admin.mapper.MenuMapper;
import com.whoiszxl.taowu.admin.service.IMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 系统菜单 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2023-03-02
 */
@Service
@RequiredArgsConstructor
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    private final MenuMapper menuMapper;

    @Override
    public Set<String> listPermissionsByAdminId(Integer adminId) {
        return menuMapper.listPermissionsByAdminId(adminId);
    }

    @Override
    public void addMenu(MenuAddCommand command) {
        boolean exists = this.lambdaQuery().eq(Menu::getName, command.getName()).exists();
        Assert.isFalse(exists, "菜单已存在");
        Menu menu = BeanUtil.copyProperties(command, Menu.class);
        this.save(menu);
    }

    @Override
    public void updateMenu(MenuUpdateCommand command) {
        boolean exists = this.lambdaQuery().eq(Menu::getName, command.getName()).exists();
        Assert.isFalse(exists, "菜单已存在");
        Menu menu = BeanUtil.copyProperties(command, Menu.class);
        this.updateById(menu);
    }

    @Override
    public void deleteMenu(List<Integer> ids) {
        this.removeBatchByIds(ids);
        this.lambdaUpdate().in(Menu::getParentId, ids).remove();
    }
}
