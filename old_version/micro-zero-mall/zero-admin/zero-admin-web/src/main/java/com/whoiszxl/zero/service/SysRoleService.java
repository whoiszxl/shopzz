package com.whoiszxl.zero.service;

public interface SysRoleService {

    /**
     * 判断一个用户是否为超级的管理员
     * @param userId
     * @return
     */
    boolean isSuperAdmin(Long userId);
    
}
