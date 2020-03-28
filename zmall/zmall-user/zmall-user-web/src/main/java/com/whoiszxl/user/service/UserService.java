package com.whoiszxl.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whoiszxl.user.entity.User;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author author
 * @since 2020-03-25
 */
public interface UserService extends IService<User> {


    /**
     * 增加用户积分
     * @param username
     * @param point
     * @return
     */
    int addUserPoints(String username, Integer point);
}
