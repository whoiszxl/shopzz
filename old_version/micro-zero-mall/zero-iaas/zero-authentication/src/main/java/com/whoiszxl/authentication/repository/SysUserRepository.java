package com.whoiszxl.authentication.repository;

import com.whoiszxl.authentication.entity.SysPrivilege;
import com.whoiszxl.authentication.entity.SysUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * 系统用户repository
 *
 * @author whoiszxl
 * @date 2021/4/8
 */
@Repository
public interface SysUserRepository extends BaseRepository<SysUser> {

    /**
     * 通过用户名和状态查找系统用户信息
     * @param username
     * @param status
     * @return
     */
    SysUser findByUsernameAndStatus(String username, Integer status);

    /**
     * 通过管理员ID获取角色代码
     * @param userId
     * @return
     */
    @Query(value = "select sr.code from sys_role as sr " +
                    "left join sys_user_role as sur " +
                    "on sr.id = sur.role_id " +
                    "where sur.user_id = :userId", nativeQuery = true)
    String getRoleCode(@Param("userId") Long userId);
}
