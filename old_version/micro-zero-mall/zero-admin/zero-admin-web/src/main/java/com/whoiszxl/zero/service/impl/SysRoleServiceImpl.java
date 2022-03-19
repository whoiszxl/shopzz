package com.whoiszxl.zero.service.impl;

import com.whoiszxl.zero.dao.SysRoleDao;
import com.whoiszxl.zero.service.SysRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleDao sysRoleDao;

    @Override
    public boolean isSuperAdmin(Long userId) {
        //管理用户的roleCode为ROLE_ADMIN的话就是超管
        String roleCode = sysRoleDao.getUserRoleCode(userId);
        return StringUtils.equals(roleCode, "ROLE_ADMIN");
    }

}
