package com.whoiszxl.taowu.admin.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.whoiszxl.taowu.admin.service.PermissionService;
import com.whoiszxl.taowu.admin.service.IMenuService;
import com.whoiszxl.taowu.admin.service.IRoleService;
import com.whoiszxl.taowu.common.constants.CommonConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * 权限服务接口实现
 * @author whoiszxl
 */
@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final IMenuService menuService;

    private final IRoleService roleService;


    @Override
    public Set<String> listPermissionsByAdminId(Integer adminId) {
        Set<String> roleList = listRoleByAdminId(adminId);
        if(roleList.contains(CommonConstants.SUPER_ADMIN)) {
            return CollUtil.newHashSet(CommonConstants.ALL_PERMISSION);
        }
        return menuService.listPermissionsByAdminId(adminId);
    }

    @Override
    public Set<String> listRoleByAdminId(Integer adminId) {
        return roleService.listRoleByAdminId(adminId);
    }
}
