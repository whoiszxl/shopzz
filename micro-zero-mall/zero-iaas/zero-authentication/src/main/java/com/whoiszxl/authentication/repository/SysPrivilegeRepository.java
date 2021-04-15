package com.whoiszxl.authentication.repository;

import com.whoiszxl.authentication.entity.SysPrivilege;
import com.whoiszxl.authentication.entity.SysUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 系统权限repository
 *
 * @author whoiszxl
 * @date 2021/4/8
 */
@Repository
public interface SysPrivilegeRepository extends BaseRepository<SysPrivilege> {

    @Query(value = "select sp.* from sys_privilege as sp " +
                    "left join sys_role_privilege as srp " +
                    "on srp.privilege_id = sp.id " +
                    "left join sys_user_role as sur " +
                    "on srp.role_id = sur.role_id " +
                    "where sur.user_id = :userId", nativeQuery = true)
    List<SysPrivilege> findByUserId(@Param("userId") Long userId);
}
