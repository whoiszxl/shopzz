package com.whoiszxl.zero.dao;

public interface SysRoleDao {

    /**
     * 获取管理用户的角色码
     * @param userId 用户ID
     * @return
     */
    String getUserRoleCode(Long userId);
}
