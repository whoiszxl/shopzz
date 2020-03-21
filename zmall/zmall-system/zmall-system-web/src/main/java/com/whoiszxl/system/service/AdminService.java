package com.whoiszxl.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whoiszxl.system.entity.Admin;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2020-03-21
 */
public interface AdminService extends IService<Admin> {

    /**
     * 用户登录
     */
    boolean login(Admin admin);
}
