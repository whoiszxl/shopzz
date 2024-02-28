package com.whoiszxl.taowu.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.whoiszxl.taowu.admin.entity.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Set;

/**
 * <p>
 * 系统菜单 Mapper 接口
 * </p>
 *
 * @author whoiszxl
 * @since 2023-03-02
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {


    @Select("select `permission` from sys_menu as sm " +
            "left join sys_role_menu as srm on sm.id = srm.menu_id " +
            "left join sys_role as sr on sr.id = srm.role_id " +
            "left join sys_admin_role as sar on sar.role_id = sr.id " +
            "where sar.admin_id = #{adminId} " +
            "and sm.type in (2,3) " +
            "and sr.`status` = 1 " +
            "and sm.`status` = 1 ")
    Set<String> listPermissionsByAdminId(@Param("adminId") Integer adminId);
}
