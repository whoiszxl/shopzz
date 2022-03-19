package com.whoiszxl.zero.dao;

import com.whoiszxl.zero.entity.SysMenu;

import java.util.List;

public interface SysMenuDao {

    /**
     * 查询所有菜单数据
     * @return
     */
    List<SysMenu> findAll();

    /**
     * 通过管理用户ID查询菜单数据
     * @param userId 用户ID
     * @return
     */
    List<SysMenu> selectMenusByUserId(Long userId);
}
