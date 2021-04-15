package com.whoiszxl.zero.dao.impl;

import com.whoiszxl.zero.dao.SysRoleDao;
import com.whoiszxl.zero.repository.SysRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SysRoleDaoImpl implements SysRoleDao {

    @Autowired
    private SysRoleRepository sysRoleRepository;

    @Override
    public String getUserRoleCode(Long userId) {
        return sysRoleRepository.getRoleCode(userId);
    }
}
