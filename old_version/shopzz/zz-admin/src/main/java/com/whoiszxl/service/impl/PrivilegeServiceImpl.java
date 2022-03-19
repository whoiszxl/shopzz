package com.whoiszxl.service.impl;

import com.whoiszxl.entity.Privilege;
import com.whoiszxl.mapper.PrivilegeMapper;
import com.whoiszxl.service.PrivilegeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 权限配置 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2021-12-06
 */
@Service
public class PrivilegeServiceImpl extends ServiceImpl<PrivilegeMapper, Privilege> implements PrivilegeService {

    @Autowired
    private PrivilegeMapper privilegeMapper;

    @Override
    public List<String> getCurrentAdminAvailPrivilegeList(Integer adminId) {
        return privilegeMapper.getCurrentAdminAvailPrivilegeList(adminId);
    }
}
