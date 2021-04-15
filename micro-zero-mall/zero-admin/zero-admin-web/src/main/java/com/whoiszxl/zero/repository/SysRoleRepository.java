package com.whoiszxl.zero.repository;

import com.whoiszxl.zero.bean.BaseRepository;
import com.whoiszxl.zero.entity.SysRole;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SysRoleRepository extends BaseRepository<SysRole> {

    /**
     * 通过管理员ID获取角色代码
     * @param userId
     * @return
     */
    @Query(value = "select sr.code from sys_role as sr " +
            "left join sys_user_role as sur " +
            "on sr.id = sur.role_id " +
            "where sur.user_id = :userId",
            nativeQuery = true)
    String getRoleCode(@Param("userId") Long userId);
}
