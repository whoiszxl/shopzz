package com.whoiszxl.service.impl;

import com.whoiszxl.entity.RoleAuthority;
import com.whoiszxl.mapper.RoleAuthorityMapper;
import com.whoiszxl.service.RoleAuthorityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色&(权限|菜单)关联表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2021-12-06
 */
@Service
public class RoleAuthorityServiceImpl extends ServiceImpl<RoleAuthorityMapper, RoleAuthority> implements RoleAuthorityService {

}
