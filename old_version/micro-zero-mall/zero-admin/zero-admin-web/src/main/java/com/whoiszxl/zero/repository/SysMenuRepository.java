package com.whoiszxl.zero.repository;

import com.whoiszxl.zero.bean.BaseRepository;
import com.whoiszxl.zero.entity.SysMenu;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SysMenuRepository extends BaseRepository<SysMenu> {

    @Query(value = "select m.* from sys_menu as m" +
                    "left join sys_role_menu as rm on rm.menu_id = m.id " +
                    "left join sys_user_role as ur on ur.role_id = rm.role_id " +
                    "where ur.user_id = :userId",
    nativeQuery = true)
    List<SysMenu> selectMenusByUserId(@Param("userId") Long userId);
}
