package com.whoiszxl.service.impl;

import com.whoiszxl.entity.Admin;
import com.whoiszxl.mapper.AdminMapper;
import com.whoiszxl.service.AdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 管理员表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2021-12-06
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

}
