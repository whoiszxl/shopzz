package com.whoiszxl.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.system.entity.Admin;
import com.whoiszxl.system.mapper.AdminMapper;
import com.whoiszxl.system.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author author
 * @since 2020-03-21
 */
@Slf4j
@Service
@Transactional
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public boolean login(Admin admin) {
        //根据登录名获取管理员信息
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("login_name", admin.getLoginName());
        Admin adminResult = adminMapper.selectOne(queryWrapper);

        if (adminResult == null){
            return false;
        }else{
            //对密码进行校验
            return BCrypt.checkpw(admin.getPassword(),adminResult.getPassword());
        }
    }
}
