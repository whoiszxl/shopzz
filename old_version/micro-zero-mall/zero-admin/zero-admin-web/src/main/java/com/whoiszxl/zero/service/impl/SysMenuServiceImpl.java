package com.whoiszxl.zero.service.impl;

import com.whoiszxl.zero.dao.SysMenuDao;
import com.whoiszxl.zero.entity.SysMenu;
import com.whoiszxl.zero.service.SysMenuService;
import com.whoiszxl.zero.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysMenuDao sysMenuDao;

    @Override
    public List<SysMenu> getMenusByUserId(Long userId) {
        // 1 如果该用户是超级管理员->>拥有所有的菜单
        if(sysRoleService.isSuperAdmin(userId)){
            return sysMenuDao.findAll(); // 查询所有
        }
        // 2 如果该用户不是超级管理员->>查询角色->查询菜单
        return sysMenuDao.selectMenusByUserId(userId);
    }
}
