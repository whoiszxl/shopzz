package com.whoiszxl.zero.dao.impl;

import com.whoiszxl.zero.dao.SysMenuDao;
import com.whoiszxl.zero.entity.SysMenu;
import com.whoiszxl.zero.repository.SysMenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SysMenuDaoImpl implements SysMenuDao {

    @Autowired
    private SysMenuRepository sysMenuRepository;

    @Override
    public List<SysMenu> findAll() {
        return sysMenuRepository.findAll();
    }

    @Override
    public List<SysMenu> selectMenusByUserId(Long userId) {
        return sysMenuRepository.selectMenusByUserId(userId);
    }
}
