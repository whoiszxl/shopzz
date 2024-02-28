package com.whoiszxl.taowu.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.whoiszxl.taowu.admin.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 角色 Mapper 接口
 * </p>
 *
 * @author whoiszxl
 * @since 2023-03-02
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {


    /**
     * 切换角色状态
     * @param roleId 角色ID
     * @return 是否切换成功
     */
    @Update("update sys_role set status = (case status when 1 then 0 else 1 end) where id = #{roleId}")
    Boolean switchStatus(Integer roleId);
}
