package com.whoiszxl.taowu.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whoiszxl.taowu.admin.cqrs.command.AdminAddCommand;
import com.whoiszxl.taowu.admin.cqrs.command.AdminRoleUpdateCommand;
import com.whoiszxl.taowu.admin.cqrs.command.AdminUpdateCommand;
import com.whoiszxl.taowu.admin.cqrs.response.AdminDetailResponse;
import com.whoiszxl.taowu.admin.entity.Admin;

import java.util.List;

/**
 * <p>
 * 管理员表 服务类
 * </p>
 *
 * @author whoiszxl
 * @since 2023-03-02
 */
public interface IAdminService extends IService<Admin> {

    /**
     * 管理后台新增管理员
     * @param command 新增命令
     * @return 是否新增成功
     */
    Boolean addAdmin(AdminAddCommand command);

    /**
     * 修改管理员角色
     * @param command 更新角色命令
     * @return 是否更新成功
     */
    Boolean updateAdminRole(AdminRoleUpdateCommand command);

    /**
     * 切换管理员状态
     * @return 是否切换成功
     * @param adminId 管理员ID
     */
    Boolean switchStatus(Integer adminId);

    /**
     * 通过管理员ID重置管理员密码
     * @param adminId 管理员ID
     * @return 是否重置成功
     */
    Boolean resetPassword(Integer adminId);

    /**
     * 获取管理员的详情信息
     * @param adminId
     * @return
     */
    AdminDetailResponse adminDetail(Integer adminId);

    /**
     * 更新管理员
     * @param adminUpdateCommand 更新参数
     * @return 是否更新成功
     */
    boolean updateAdmin(AdminUpdateCommand adminUpdateCommand);

    /**
     * 批量删除管理员
     * @param ids 管理员id集合
     * @return 是否删除成功
     */
    boolean adminDelete(List<Integer> ids);
}
