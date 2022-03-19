package com.whoiszxl.mapper;

import com.whoiszxl.entity.Privilege;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 权限配置 Mapper 接口
 * </p>
 *
 * @author whoiszxl
 * @since 2021-12-06
 */
public interface PrivilegeMapper extends BaseMapper<Privilege> {

    @Select("select code " +
            "from sys_privilege where 1 = 1 " +
            "and id in (" +
            "select authority_id from sys_role_authority sra " +
            "INNER JOIN sys_admin_role sar on sra.role_id = sar.role_id " +
            "INNER JOIN sys_role sr on sr.id = sra.role_id " +
            "where sar.admin_id = #{adminId} and sr.`status` = 1 and sra.authority_type = 2" +
            ")")
    List<String> getCurrentAdminAvailPrivilegeList(Integer adminId);
}
