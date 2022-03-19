package com.whoiszxl.zero.service;

import com.whoiszxl.zero.entity.SysMenu;

import java.util.List;

public interface SysMenuService {

    /**
     * 通过用户的id 查询用户的菜单数据
     * @param userId
     * @return
     */

    List<SysMenu> getMenusByUserId(Long userId);
}
