package com.whoiszxl.taowu.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.taowu.admin.service.PermissionService;
import com.whoiszxl.taowu.admin.cqrs.command.AdminAddCommand;
import com.whoiszxl.taowu.admin.cqrs.command.AdminRoleUpdateCommand;
import com.whoiszxl.taowu.admin.cqrs.command.AdminUpdateCommand;
import com.whoiszxl.taowu.admin.cqrs.response.AdminDetailResponse;
import com.whoiszxl.taowu.admin.entity.Admin;
import com.whoiszxl.taowu.admin.entity.AdminRole;
import com.whoiszxl.taowu.admin.mapper.AdminMapper;
import com.whoiszxl.taowu.admin.service.IAdminRoleService;
import com.whoiszxl.taowu.admin.service.IAdminService;
import com.whoiszxl.taowu.admin.service.IRoleService;
import com.whoiszxl.taowu.common.constants.CommonConstants;
import com.whoiszxl.taowu.common.utils.SecureUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 管理员表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2023-03-02
 */
@Service
@RequiredArgsConstructor
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

    private final IAdminRoleService adminRoleService;

    private final AdminMapper adminMapper;

    private final PermissionService permissionService;

    private final IRoleService roleService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean addAdmin(AdminAddCommand command) {
        //1. 校验此用户是否存在
        boolean exists = this.lambdaQuery().eq(Admin::getUsername, command.getUsername()).exists();
        Assert.isFalse(exists, "用户已存在");

        //2. 新增
        Admin admin = BeanUtil.copyProperties(command, Admin.class);
        this.save(admin);

        //3. 密码设置
        this.lambdaUpdate()
                .set(Admin::getPassword,
                        SecureUtils.passwordMd5(command.getPassword(), CommonConstants.PASSWORD_SALT));

        //4. 角色设置
        adminRoleService.save(command.getRoleIds(), admin.getId());
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateAdminRole(AdminRoleUpdateCommand command) {
        Admin admin = adminMapper.selectById(command.getId());
        Assert.notNull(admin, "管理员不存在");

        adminRoleService.resetRole(command.getRoleIds(), command.getId());
        return true;
    }

    @Override
    public Boolean switchStatus(Integer adminId) {
        return adminMapper.switchStatus(adminId);
    }

    @Override
    public Boolean resetPassword(Integer adminId) {
        Admin admin = this.getById(adminId);
        admin.setPassword(SecureUtils.passwordMd5(CommonConstants.DEFAULT_PASSWORD, CommonConstants.PASSWORD_SALT));
        baseMapper.updateById(admin);
        return true;
    }

    @Override
    public AdminDetailResponse adminDetail(Integer adminId) {
        Set<String> roles = permissionService.listRoleByAdminId(adminId);
        Set<Integer> roleIds = roleService.listRoleIdByAdminId(adminId);
        Admin admin = this.getById(adminId);
        AdminDetailResponse detailResponse = BeanUtil.copyProperties(admin, AdminDetailResponse.class);
        detailResponse.setRoles(roles);
        detailResponse.setRoleIds(roleIds);
        return detailResponse;
    }

    @Override
    public boolean updateAdmin(AdminUpdateCommand adminUpdateCommand) {
        String username = adminUpdateCommand.getUsername();
        boolean exists = this.lambdaQuery().eq(Admin::getUsername, username).ne(Admin::getId, adminUpdateCommand.getId()).exists();
        Assert.isFalse(exists, "此用户已存在，请更换用户名");

        Admin admin = BeanUtil.copyProperties(adminUpdateCommand, Admin.class);
        if(StrUtil.isNotBlank(admin.getPassword())) {
            admin.setPassword(SecureUtils.passwordMd5(admin.getPassword(), CommonConstants.PASSWORD_SALT));
        }else {
            admin.setPassword(null);
        }

        adminMapper.updateById(admin);

        adminRoleService.save(adminUpdateCommand.getRoleIds(), admin.getId());

        return true;
    }

    @Override
    public boolean adminDelete(List<Integer> ids) {
        //1. 删除管理员信息
        this.removeBatchByIds(ids);

        //2. 删除管理员与角色的关联信息
        adminRoleService.lambdaUpdate().in(AdminRole::getAdminId, ids).remove();
        return true;
    }

    @Override
    public boolean save(Admin entity) {
        return super.save(entity);
    }
}
